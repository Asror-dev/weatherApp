# API Documentation


1. Authentication API
1.1 Register
- Path: `POST /api/auth/register`  
- Description: Registers a new user in the system.  
- Request Body (JSON):

```json
{
  "name": "your_name",
  "surname":"your_surname",
  "username": "your_username",
  "password": "your_password",  
}

1.2 Login
- Path: `POST /api/auth/login`  
- Description: Login a user in the system.  
- Request Body (JSON):
{
 "username": "your_username",
  "password": "your_password",
}

2.2 Weather data API'
- Path: `POST /api/weather/data`  
- Description: Getting Weather Information in the System.  
- Request Body (JSON):
{
  "countries":  ["your_city","your_city1"]    
}
 
