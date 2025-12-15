from langchain.text_splitter import TokenTextSplitter
from langchain.schema import Document
from typing import List
import uuid


def chunk_data(documents:List[Document])->List[Document]:
    
    splitter = TokenTextSplitter(
        chunk_size=300,  # Tokens, not characters
        chunk_overlap=50
    )
    
    chunked_docs = []

    for doc in documents:
        chunks = splitter.split_text(doc.page_content)
        print("Chunked into",len(chunks),"on page: ",doc.metadata["source"])
        for i, chunk in enumerate(chunks):
            chunked_docs.append(
                Document(
                    page_content=chunk,
                    metadata={
                        "chunk_id": str(uuid.uuid4()),      # unique per chunk
                        "source": doc.metadata["source"],   # URL
                        "parent_url": doc.metadata["source"],
                        "title": doc.metadata["title"],
                        "chunk_index": i
                    }
                )
            )
    return chunked_docs