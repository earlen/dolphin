## Development Notes
- Node LTS version is 20.10.0 LTS, but cryptographic functions don't use OpenSSL 3.0, which became the standard with Node versions 17+. Used NVM and ran with newest LTS version of Node 16.
- There is a client side check for whether the order amount will exceed the capacity + current stock. This could likely be bypassed, and it may be better to use a server side check. However, for this application and its use case I believe this will suffice. If this application was updated to include an ordering function, I would implement a server side check
- No logging implementation has been added.
## Future Development and Features
- A button that gives the cost to fully-restock.
- Ability to adjust capacity.
- An output that shows a breakdown of the distributors that will be used for the optimal purchase.
- If distributors began to offer different unit costs depending on volume, this could be handled in the backend with some new math.
- Update total cost real-time as the user changes order amounts.
- Verify integrity of server resources by ensuring header names match expectations.
- Migrate server resources into a relational database (i.e. MySQL) and use a query to find the lowest price.
- Allow user to input a budget, and use sales history to optimize restocking.
- Add useEffect hook to get low stock items on render. 


## System Requirements
- Latest LTS version of Node
- Java 8

## Getting Started

Clone this repo `git clone https://github.com/TopBloc/code-challenge.git`

Run the frontend code within the `/client` folder:

```bash
cd client
npm install
npm run start
```

After running previous commands, you should see a website with instructions at `http://localhost:3000`.

Open the `/server` folder as a Maven project within a Java IDE and run the project. We suggest using IntelliJ IDEA.

## Submission

1. Create a new repository within GitHub and name it as your favorite animal (ex. Sloth, Zebra)
2. Set the remote origin of this cloned project to your newly created GitHub repository:
```
git remote set-url --push origin https://github.com/<github_username>/<favorite_animal>
```
3. Push your completed code challenge!
