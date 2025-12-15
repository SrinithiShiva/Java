from langchain_community.document_loaders import RecursiveUrlLoader
from langchain.schema import Document
from typing import List

def crawlPage(baseUrl:str,max_depth: int=2,max_pages: int=2)->List[Document]:
    loader = RecursiveUrlLoader(
        url=baseUrl,
        max_depth=max_depth,
        extractor=None #To get raw html
    )
    documents = loader.load()
    documents = documents[:max_pages]
    
    #Inspect
    print(f"Crawled {len(documents)} document(s)")
    for doc in documents:
        print("URL:", doc.metadata["source"])
        print("Title:", doc.metadata["title"])
        print("HTML length:", len(doc.page_content))
        print("-" * 40)
    return documents