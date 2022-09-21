There are mainly three levels of data abstraction and we divide it into three levels in order to achieve Data Independence.

1. View Level
2. Conceptual Level
3. Physical Level

## Level 1 : Physical Level or Internal Schema Lowest level of Data Abstraction.

• It defines how data are stored.

• It tells the actual location of the data that is being stored by the user.

• The Database Administrators(DBA) decide that which data should be kept at which particular disk drive, how the data has to be arranged, where it has to be stored etc.

• They decide if the data has to be centralized or distributed.

• It totally depends on the DBA, how he/she manages the database at the physical level.

## Level 2 : Conceptual Level or Logical Level

• This level defines what data are stored in database & What relationship exists among these data.

• Logical levels decide structure of entire database.

• DBA use the logical level for abstraction purpose.

Example :

• Let us take an example where we use the relational model for storing the data.

• We have to store the data of a student, the columns in the student table will be student_name, age, mail id, roll no etc.

• Though the data is stored in the database but the structure of the tables like the student table, teacher table, books table, etc are defined here in the conceptual level or logical level.

• Also, how the tables are related to each other are defined here.

## Level 3 : View Level or External Schema

• This level tells the application about how the data should be shown to the user.

• Different views of same database can be created for user to interact with database for user friendly approach.

Example :

- If we have a login-id and password in a university system, then as a student, we can view our marks, attendance, fee structure, etc. But the faculty of the university will have a different View.
- He will have options like salary, edit marks of a student, enter attendance of the students, etc. So, both the student and the faculty have a different view.
- By doing so, the security of the system also increases.
- In this example, the student can't edit his marks but the faculty who is authorized to edit marks can edit the student's marks.