from langchain_openai import ChatOpenAI,OpenAIEmbeddings
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain_community.vectorstores import Chroma
from langchain.schema.output_parser import StrOutputParser
from langchain.schema.runnable import RunnablePassthrough
from langchain_community.document_loaders import WebBaseLoader
from langchain.text_splitter import TokenTextSplitter
from langchain.memory import ConversationSummaryMemory
from langchain.chains import LLMChain
import os
from dotenv import load_dotenv
load_dotenv()

## ========= STEP 1:Crawl a website and create documents =========
loader = WebBaseLoader("https://en.wikipedia.org/wiki/Mother_Teresa")
documents = loader.load()
print(f"Loaded {len(documents)} document(s)")

## ========= STEP 2:Clean the documents and chunk them =========
splitter = TokenTextSplitter(
    chunk_size=300,  # Tokens, not characters
    chunk_overlap=50
)
chunks = splitter.split_documents(documents)
print(f"Split into {len(chunks)} chunks")

## ========= STEP 3:Create embeddings =========  
embeddings = OpenAIEmbeddings(model="text-embedding-3-small")

## ========= STEP 4:Store in Vector database =========
vectorstore = Chroma.from_documents(
    documents=chunks,
    embedding=embeddings,
    persist_directory="./chroma_db"
)
print("Stored in vector database")

## ========= STEP 5:Retrieve the relevant chunks =========
retriever = vectorstore.as_retriever(search_kwargs={"k": 5})

def format_docs(docs):
    return "\n\n".join(doc.page_content for doc in docs)

def get_context(question: str) -> str:
    docs = retriever.invoke(question)
    return format_docs(docs)

# ========== STEP 6: Build RAG Chain ==========
model = ChatOpenAI(
    model="gpt-4.1-mini",
    temperature=0,
    max_tokens=1000,
    api_key=os.getenv("OPENAI_API_KEY")
)

memory = ConversationSummaryMemory(
     return_messages=True, 
     memory_key="history", 
     llm=model,  # Needs LLM to generate summaries
     input_key="question",   # THIS IS CRITICAL
     output_key="text"      # default LLMChain output key
)

prompt = ChatPromptTemplate.from_messages([
    ("system","Answer the question based on the context and conversation history.If you cannot answer from the context, say \"I don't have information about that.\""),
    MessagesPlaceholder(variable_name="history"),
    ("user","Context:\n{context}\n\nQuestion:\n{question}")
])

rag_chain = LLMChain(
    prompt=prompt,
    llm=model,
    memory=memory,
)

# ========== STEP 7: Query RAG Chain ==========
def chat(question):
    context = get_context(question)
    result = rag_chain.invoke({
        "question": question,
        "context": context
    })
    return result["text"]

questions = [
    "who is described here?",
    "Where is she born?",
    "What service did she do?",
    "What's the policy on overtime pay?",  # Not in context
    "What is the first question asked?"
]
if __name__ == "__main__":
    for q in questions:
        print(f"\nQ: {q}")
        print(f"A: {chat(q)}")
