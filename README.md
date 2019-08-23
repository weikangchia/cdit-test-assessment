# CDIT Test Assessment

## Developer Guide
### Pre-requisites
1. Postgres Server
2. Java Jdk
3. Git
4. IntelliJ IDEA

### Getting Started
1. Clone this [repo](https://github.com/weikangchia/gds-technical-assessment.git)
2. Open this project using your IntelliJ IDEA (If this is your first time opening the project from your IntelliJ IDEA, it will take some time for the gradle script to setup)
3. Setup a Postgres Server
   - Create an empty database, named `cdit`
   - Go to `src/main/resources` and open `application.properties` file
     - Update your Postgres server url (spring.datasource.url)
     - Update your Postgres server username (spring.datasource.username)
     - Update your Postgres server password (spring.datasource.password)
4. Run the web application

### APIs
1. Upload user<br/>
   POST: `/users/upload`<br/>
   *Body (formdata)*<br/>
   - file
2. Get users<br/>
   GET: `/users`

### Note
1. CSV File
   To upload the user csv file, you need to prepare the csv file that meet the following requirements:
   - name
   - salary (in cents)
   
   e.g.
   ```
   name,salary
   John,250005
   Mary Posa,400000
   ```
