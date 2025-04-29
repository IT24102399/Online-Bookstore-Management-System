#!/bin/bash

echo "====================================================="
echo "    ONLINE BOOKSTORE MANAGEMENT SYSTEM LAUNCHER"
echo "====================================================="
echo

# Create data directory if it doesn't exist
echo "Creating data directory for file storage..."
mkdir -p data

# Remove existing JSON files to start fresh
echo "Removing existing data files to start fresh..."
rm -f data/*.json

# Create empty JSON files for data storage
echo "Creating empty JSON data files..."
echo "[]" > data/users.json
echo "[]" > data/books.json
echo "[]" > data/orders.json
echo "[]" > data/payments.json
echo "[]" > data/reviews.json

echo
echo "Building and running the application..."
echo

# Run the application using Maven wrapper
./mvnw spring-boot:run

echo
echo "====================================================="
echo "Application terminated. Thank you for using the"
echo "Online Bookstore Management System!"
echo "====================================================="
