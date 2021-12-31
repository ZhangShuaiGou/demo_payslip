# README

Hello, this project is an assignment of a job interview. It is a REST API for generating employees' payroll. 

This **README** will explain the conception of this project and then show how to run the project.



## Introduction

When supplied with employee details: first name, last name, annual salary (positive integer) and super rate (0% - 50% inclusive), payment period (from start date to end date), the program will generate pay slip information which includes name, pay period, gross income, income tax, net income and super.

The calculation details will be the following:

* pay period = per calendar month • gross income = annual salary / 12 months 

* income tax = based on the tax table provided below 

* net income = gross income - income tax 

* super = gross income x super rate 

The income tax is calculating via rate in following table:

| Taxable income     | Tax on this income                          |
| ------------------ | ------------------------------------------- |
| $0 - $18,200       | Nil                                         |
| $18,201 - $37,000  | 19c for each $1 over $18,200                |
| $37,001 - $87,000  | $3,572 plus 32.5c for each $1 over  $37,000 |
| $87,001 - $180,000 | $19,822 plus 37c for each $1 over  $87,000  |
| $180,001 and over  | $54,232 plus 45c for each $1 over  $180,000 |



For example, the payment in March for an employee with an annual salary of $60,050 and a super rate of 9% is: 

* pay period = Month of March (01 March to 31 March) 

* gross income = 60,050 / 12 = 5,004.16666667 (round down) = 5,004 

* income tax = (3,572 + (60,050 - 37,000) x 0.325) / 12 = 921.9375 (round up) = 922 

* net income = 5,004 - 922 = 4,082 

* super = 5,004 x 9% = 450.36 (round down) = 450 

We make the tax table flexible. therefore, you need to input a tax table before you use it. (POST {api_address}/taxtable).
The tax table input format is: 
```json
[
    {
        "minTaxThreshold":0,
        "maxTaxThreshold":18200,
        "taxBase":0,
        "taxRate":0.0            
    },
    {
        "minTaxThreshold":18201,
        "maxTaxThreshold":37000,
        "taxBase":0,
        "taxRate":0.19            
    },
    {
        "minTaxThreshold":37001,
        "maxTaxThreshold":87000,
        "taxBase":3572,
        "taxRate":0.325            
    },
    {
        "minTaxThreshold":87001,
        "maxTaxThreshold":180000,
        "taxBase":19822,
        "taxRate":0.37            
    },
    {
        "minTaxThreshold":180001,
        "maxTaxThreshold":2147483647,
        "taxBase":54232,
        "taxRate":0.45            
    }
]
```


The input format is adopted in ***JSON*** (this is also an example request body test case):

```json
[
  {
  	"firstName":"David",  
   	"lastName":"Rudd",	
   	"annualSalary":60050, 
    "payPeriod":"01 March – 31 March",
  	"superRate":0.09 
  },
  {
   	"firstName":"Ryan",
   	"lastName":"Chen",
   	"annualSalary":120000,
    "payPeriod":"01 March – 31 May",
   	"superRate":0.1
  }
]
```

The output format is also adopted in ***JSON*** (this is also the response body of the above request):

```JSON
[
    {
        "employee": {							          
            "firstName": "David",
            "lastName": "Rudd",
            "annualSalary": 60050,
            "superRate": 0.09,
            "paymentMonth": 1
        },
        "fromDate": "01 March",					   
        "toDate": "31 March",				      	
        "grossIncome": 5004,		      			
        "incomeTax": 922,					         	
        "super": 450,						          	
        "netIncome": 4082					        	
    },
    {
        "employee": {
            "firstName": "Ryan",
            "lastName": "Chen",
            "annualSalary": 120000,
            "superRate": 0.1,
            "paymentMonth": 3
        },
        "fromDate": "01 March",
        "toDate": "31 May",
        "grossIncome": 30000,
        "incomeTax": 2669,
        "super": 3000,
        "netIncome": 27331
    }
]
```



## Some assumptions

Payroll calculation is complex in real world. Thus, in this API, here are some assumptions we make to simplify the process.

* #### We assume that the input is rigid in format

​		The format is displayed above. But we would like to re-emphasize it.

```json
 [ 
  {
  	"firstName":"David",					
   	"lastName":"Rudd",					
   	"annualSalary":60050,					
    "payPeriod":"01 March – 31 March",			
  	"superRate":0.09							
  }
 ]
```



* #### We assume the input is all valid

  To focus on the implement of the REST part, we assume that all the input is valid. 

  In the future work, we will add the related value check functions. 



* #### We assume the input payment period is within one year

​		According to the input format, we did not record the `year`, therefore, this API only generate the payroll within one year.



* #### We assume the minimum unit of payment is Month 

  By this assumption, we do not furtherly divide payment by days of months, and the minimum of payment period is one month. 

  For example, ` "payPeriod":"01 March – 31 March" ` will return the same payment as `"payPeriod":"01 March – 02 March"`, and the result is total one month payment of March.  

  

## Instruction

To run this API, we recommend using  [***Postman***](https://www.postman.com/) to send requests and receive responses. You can find more useful information through its official website. 

Note that this API is developed by [***Spring-boot 2.6.1***](https://spring.io/projects/spring-boot) and ***Java 11***

This section we provided three methods, however, they are only different in how to access the API. The part how to use ***Postman*** is same. To make it easy to read, we only explain how to use ***Postman*** in the first method.

### Method one

This method refers to clone this repo and build it on your local machine. 

​	**Step 1**: Clone this repo to your local machine.

​	**Step 2**: Build this repo with your favourite IDEA (we recommend and used [IntelliJ IDEA](https://www.jetbrains.com/idea/)).

​	**Step 3**: Open your Postman.

​	**Step 4**: Paste this link (localhost:8080/employee) to the URL bar.

​	**Step 5**: Select `POST` as request method.  

​	**Step 6**: Select `raw` data with `JSON`  format under `Body` column. 

​	**Step 7**: Paste or type request body and  click send. You can put multiple employees or single employee into one request body.

​	**Step 8**: Check the response body in below panel.

Below is an example of how manipulate Postman.

![image-20211223202859606](/src/main/resources/image-20211223202859606.png)

### Method two

An image of this API is built and push to the Docker hub. Hit this [link](https://hub.docker.com/r/zhangshuaigou/demo_payslip) to see more detail. 

Once you pull this image to your local machine, you can run it in your docker. 

Note, you need to check your docker IP and use it to access the API in ***Postman***.

### Method three (Recommended)

We have already deployed this API on AWS ECS service. You can directly put below link to Postman URL bar to access the service.

`13.239.138.72:8080/employee`





**Thanks for your reading and please feel free to create issues for answers to your further questions.**

