from langchain_community.vectorstores import Chroma
from langchain.memory import ConversationBufferMemory
from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain.chains import LLMChain
import os
from dotenv import load_dotenv
load_dotenv()

def format_docs(docs):
    return "\n\n".join(doc.page_content for doc in docs)

def get_context(vectorstore:Chroma,question: str) -> str:
    retriever = vectorstore.as_retriever(search_kwargs={"k": 5})
    docs = retriever.invoke(question)
    return format_docs(docs)

def generateRagChain():

    model = ChatOpenAI(
        model="gpt-4.1-mini",
        temperature=0,
        max_tokens=1000,
        api_key=os.getenv("OPENAI_API_KEY")
    )

    memory = ConversationBufferMemory(
        return_messages=True, 
        memory_key="history", 
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
    return rag_chain