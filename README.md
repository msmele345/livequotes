# livequotes  
### A back end application that generates Stock Quotes and routes them to several JMS clients
    - See interstellar-exchange and stock-loader for JMS clients tied to this application.

FLOW:
1. QuoteLoader is called on Startup. This uses the QuoteProcessor to generate quote objects containing bid/ask prices for a stock symbol and saves them in SQL.
2. QuoteLoader sends saved quotes to quotes JMS queue for processing.
3. QuoteListener picks up quote messages from JMS quotes queue and calls the Orchestrator for further processing.
4. OutboundQuoteOrchestrator splits quote up into bid and ask objects, then saves them to SQL for trade consideration at the interstellar-exchange
5. OutboundQuoteOrchestrator then sends bid and ask objects to outbound queues that are picked up by other applications.
6. Stock-Loader or interstellar exchange applications picks them up to match and persist Trades.   


