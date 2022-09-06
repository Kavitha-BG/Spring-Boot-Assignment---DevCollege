# Spring-Boot-Assignment---DevCollege

Course Entity should contains below mentioned attributes:
	Course Id: String (Auto Generated: Start with CRS0001)
	Course Name: String
	Course Description: String
	No Of Registration Allowed: Integer
	Course Fee: Float
	Course Duration (in minutes): Integer  
	Course Tag: String (comma separates values)

Student Entity should contains below mentioned attributes:
	Student Id: String (Auto Generated: Start with STD0001)
	Student Name: String
	Highest qualification: String
	Student Contact No: String
	Wallet Amount: Float

Enrolment Entity should contains below mentioned attributes:
	Enrolment Id: String (Auto Generated: Start with EN0001)
	Student Id: String
	Course Id: String
	Course Start datetime
	Course End datetime
	Course Status: String (Allocated, In Progress, Completed, Cancelled)


Note: Highest Qualification and Course Tag must belong to from following list:
B.E, B.Tech, Diploma, M.E, M.Tech., M.Phil., MS, BBA, BCom, BSc, MSc, BCA, MCA, LLB, MBBS, MBA
