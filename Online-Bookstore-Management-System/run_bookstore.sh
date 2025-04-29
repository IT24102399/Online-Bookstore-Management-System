#!/bin/bash

echo "==================================================="
echo "    ONLINE BOOKSTORE MANAGEMENT SYSTEM LAUNCHER"
echo "==================================================="
echo

echo "Creating data directory for file storage..."
mkdir -p data

echo "Creating empty JSON files for data storage..."
echo "[]" > data/users.json
echo "[]" > data/books.json
echo "[]" > data/orders.json
echo "[]" > data/payments.json
echo "[]" > data/reviews.json

echo
echo "Compiling and running the application..."
echo
echo "NOTE: This is a simplified launcher for demonstration purposes."
echo "The full application requires Spring Boot with all dependencies."
echo
echo "Starting the demo version instead..."
echo

javac BookstoreDemo.java
java BookstoreDemo

echo
echo "==================================================="
echo "Application terminated. Thank you for using the"
echo "Online Bookstore Management System!"
echo "==================================================="
