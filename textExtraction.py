from bs4 import BeautifulSoup
from langchain.schema import Document
from typing import List

def clean_html_documents(raw_docs: List[Document]) -> List[Document]:
    
    cleaned_docs = []

    for doc in raw_docs:
        soup = BeautifulSoup(doc.page_content, "html.parser")

        # ---- Remove noisy tags ----
        for tag in soup([
            "script", "style", "nav", "header",
            "footer", "aside", "form", "noscript"
        ]):
            tag.decompose()

        # ---- Extract title ----
        title = soup.title.string.strip() if soup.title else "No title Given"

        # ---- Extract visible text ----
        text = soup.get_text(separator=" ", strip=True)

        # ---- Normalize whitespace ----
        text = " ".join(text.split())

        cleaned_docs.append(
            Document(
                page_content=text,
                metadata={
                    "source": doc.metadata.get("source"),
                    "title": title
                }
            )
        )

    return cleaned_docs
