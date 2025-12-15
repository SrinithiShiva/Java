from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma
from langchain.schema import Document
from typing import List
import os
from dotenv import load_dotenv
load_dotenv()

def embedAndStore(chunks:List[Document])->Chroma:
    embeddings = OpenAIEmbeddings(model="text-embedding-3-small")

    vectorstore = Chroma.from_documents(
        documents=chunks,
        embedding=embeddings,
        persist_directory="./chroma_db"
    )
    print("Stored in vector database")
    return vectorstore