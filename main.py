from fastapi import FastAPI
from pydantic import BaseModel
from crawling import crawlPage
from textExtraction import clean_html_documents
from chunking import chunk_data
from embeddings import embedAndStore
from rag import get_context,generateRagChain
from langchain.schema import Document
from typing import List
from langchain.chains import LLMChain

app = FastAPI()

class CrawlData(BaseModel):
    baseUrl:str

class QuestionRequest(BaseModel):
    question: str

### =========== Request Mapping starts =======================
@app.post("/crawl")
def crawl(req:CrawlData):
    if(req.baseUrl.strip()==""):
        return {"message":"failure"}
    crawledDoc=crawlPage(req.baseUrl)
    cleanedDoc=clean_html_documents(crawledDoc)
    chunks = chunk_data(cleanedDoc)
    app.state.vectorStore = embedAndStore(chunks)
    return {"message": "success"}


@app.post("/ask")
def ask_question(req: QuestionRequest):
    if(req.question.strip()==""):
        return {"message":"No Information Found"}
    
    if not hasattr(app.state, "vectorStore"):
        return {"message":"No Data crawled yet"}
    
    if not hasattr(app.state, "ragchain"):
        print("Generating ragChain for the first time with memory to store conversation history")
        app.state.ragchain=generateRagChain()
    
    context = get_context(app.state.vectorStore,req.question)
    result = app.state.ragchain.invoke({
        "question": req.question,
        "context": context
    })
    return result["text"]
    
    

