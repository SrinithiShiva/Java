# Crawling & RAG-based Q&A Support Bot

## Project Overview

This project implements a **Retrieval-Augmented Generation (RAG) chatbot** using **FastAPI, LangChain, ChromaDB, and OpenAI models**, focused on crawling and querying the **official Python documentation**.

The system crawls content starting from:

```
https://docs.python.org/3/tutorial/
```

It then allows users to ask natural language questions about Python concepts based strictly on the crawled documentation.

Key capabilities:

* Crawl Python tutorial pages
* Clean and extract meaningful text from HTML
* Chunk documentation into manageable segments
* Generate embeddings and store them in ChromaDB
* Answer questions using RAG
* Maintain conversational context using LangChain memory

The API exposes two endpoints:

* `/crawl` – crawl and index Python documentation
* `/ask` – query the indexed documentation

---

## High-level Architecture

```
User → FastAPI → RAG Chain
                  ├── Chroma Vector Store
                  ├── Retriever (Top-K search)
                  ├── Prompt (Context + History)
                  ├── OpenAI LLM
                  └── Conversation Memory
```

---

## Environment Setup

Create a `.env` file in the project root:

```env
OPENAI_API_KEY=your_openai_api_key_here
```

Install dependencies:

```bash
pip install -r requirements.txt
```

---

## Steps to Run the Crawler

1. Start the FastAPI server:

```bash
uvicorn main:app --reload
```

2. Call the **/crawl** endpoint:

```http
POST /crawl
Content-Type: application/json

{
  "baseUrl": "https://docs.python.org/3/tutorial/"
}
```

3. Internal processing steps:

   * Python documentation pages are crawled
   * HTML content is cleaned (navigation, scripts, noise removed)
   * Text is chunked using token-based chunking
   * Embeddings are generated using OpenAI
   * Chunks are stored in Chroma vector database
   * Vector store is cached in application state

4. Successful response:

```json
{
  "message": "success"
}
```

---

## How to Test the `/ask` Endpoint

Once the crawl step is completed, questions can be asked against the Python documentation.

### Endpoint

```http
POST /ask
Content-Type: application/json
```

### Request Body

```json
{
  "question": "What is a list in Python?"
}
```

---

## Example Questions and Answers

### Question

```text
What is a list in Python?
```

### Answer

```text
A list in Python is a mutable sequence type used to store collections of items, which can be of different types.
```

---

### Question

```text
How do you define a function in Python?
```

### Answer

```text
A function in Python is defined using the def keyword, followed by the function name, parentheses, and a colon.
```

---

### Question

```text
What does the pass statement do?
```

### Answer

```text
The pass statement is a null operation; it does nothing and is used as a placeholder where a statement is syntactically required.
```

---

### Question (Out of Context)

```text
What is the leave policy for employees?
```

### Answer

```text
I don't have information about that.
```

---

## Conversation Memory

* The chatbot uses **LangChain conversation memory** to retain context
* Memory is attached to the RAG chain and reused across requests
* This enables follow-up questions such as:

  * "Explain it with an example"
  * "What was my previous question?"

⚠️ Memory is **global and shared** (single-user design).

---

## Limitations

* Single-user memory (not session-based)
* Vector store exists only in server memory
* No authentication or access control
* Large documentation sites may increase memory usage
* No persistence of conversation history after restart

---

## Future Improvements

* Session-based or user-specific memory
* Persistent vector store loading on startup
* Semantic chunking instead of fixed token chunking

---

## Tech Stack

* **FastAPI** – API framework
* **LangChain** – RAG orchestration
* **ChromaDB** – Vector database
* **OpenAI** – Embeddings and LLM
* **Pydantic** – Request validation
* **Uvicorn** – ASGI server

---

## Summary

This project demonstrates an end-to-end **RAG system over Python documentation**, combining crawling, vector search, conversational memory, and LLM reasoning. It serves as a strong foundation for building documentation assistants and knowledge-based chatbots.
