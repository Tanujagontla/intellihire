<<<<<<< HEAD
# intellihire
=======
# IntelliHire Job Portal

A comprehensive job portal application built with Java Spring Boot and Bootstrap, featuring role-based access control for Admins, HR Employees, and Candidates.

## 🚀 Features

### For Admins
- **Dashboard**: View comprehensive statistics and metrics
- **User Management**: Manage all users (Admins, Employees, Candidates)
- **Job Management**: Oversee all job postings across companies
- **Category Management**: Create and manage job categories
- **Company Management**: Add and manage company profiles
- **Application Oversight**: Monitor all job applications

### For HR Employees
- **Job Posting**: Create and manage job postings for their company
- **Application Management**: Review and manage applications for their jobs
- **Candidate Database**: Access candidate profiles and information
- **Company Profile**: Update company information and details

### For Candidates
- **Job Search**: Browse and search available job opportunities
- **Job Applications**: Apply for jobs with cover letters
- **Application Tracking**: Monitor application status and progress
- **Profile Management**: Update personal and professional information
- **Resume Management**: Upload and manage resume/CV

## 🛠 Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (Authentication & Authorization)
- **Spring Data JPA** (Database Operations)
- **MySQL** (Database)
- **Maven** (Dependency Management)

### Frontend
- **Thymeleaf** (Template Engine)
- **Bootstrap 5.3** (UI Framework)
- **Font Awesome** (Icons)
- **Custom CSS** (Styling)

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 17** or higher installed
- **MySQL 8.0** or higher installed and running
- **Maven 3.6** or higher installed
- **IDE** (Eclipse, IntelliJ IDEA, or VS Code)

## 🔧 Setup Instructions

### 1. Database Setup

1. **Install MySQL** and start the MySQL service
2. **Create Database** (Optional - application will create it automatically):
   \`\`\`sql
   CREATE DATABASE intellihire_db;
   \`\`\`
3. **Update Database Configuration** in `src/main/resources/application.properties`:
   \`\`\`properties
   spring.datasource.url=jdbc:mysql://localhost:3306/intellihire_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   \`\`\`

### 2. Project Setup

1. **Clone or Download** the project
2. **Open in Eclipse**:
   - File → Import → Existing Maven Projects
   - Browse to the project folder
   - Select the project and click Finish

3. **Maven Dependencies**:
   - Right-click project → Maven → Reload Projects
   - Or run: `mvn clean install`

### 3. Running the Application

#### Option 1: From Eclipse
1. Right-click on `IntelliHireJobPortalApplication.java`
2. Run As → Java Application

#### Option 2: From Command Line
\`\`\`bash
mvn spring-boot:run
\`\`\`

#### Option 3: Using JAR
\`\`\`bash
mvn clean package
java -jar target/job-portal-0.0.1-SNAPSHOT.jar
\`\`\`

### 4. Access the Application

- **Application URL**: http://localhost:8080
- **Login Page**: http://localhost:8080/login

## 👥 Default User Credentials

The application comes with pre-configured demo users (all passwords are "password"):

### Admin User
- **Username**: `admin`
- **Password**: `password`
- **Role**: ADMIN
- **Access**: Full system administration

### HR Manager
- **Username**: `hr_manager`
- **Password**: `password`
- **Role**: EMPLOYEE
- **Access**: Job posting and application management

### Candidate
- **Username**: `candidate1`
- **Password**: `password`
- **Role**: CANDIDATE
- **Access**: Job browsing and application submission

**Note**: The application will automatically create these demo users on first startup if they don't exist.

## 🔗 API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /jobs` - Browse jobs
- `GET /jobs/{id}` - Job details
- `POST /login` - User login
- `GET /register` - Registration page

### Job Management APIs
- `GET /api/jobs` - Get all active jobs
- `GET /api/jobs/{id}` - Get job by ID
- `POST /api/jobs` - Create new job (Employee/Admin)
- `PUT /api/jobs/{id}` - Update job (Employee/Admin)
- `DELETE /api/jobs/{id}` - Delete job (Admin only)
- `GET /api/jobs/search` - Search jobs

### Application APIs
- `POST /api/applications` - Apply for job (Candidate)
- `GET /api/candidate/applications` - Get my applications (Candidate)
- `PUT /api/applications/{id}/status` - Update application status (Employee/Admin)

### Admin APIs
- `GET /api/admin/users` - Get all users
- `GET /api/admin/statistics` - Get dashboard statistics
- `POST /api/admin/companies` - Create company
- `POST /api/admin/categories` - Create job category

## 📮 Postman Testing

### Import Collection
1. Open Postman
2. Click Import
3. Select the `IntelliHire_API_Collection.postman_collection.json` file
4. The collection will be imported with all endpoints

### Environment Setup
Create a new environment in Postman with:
- **Variable**: `base_url`
- **Value**: `http://localhost:8080`

### Authentication
Most endpoints require authentication. Use Basic Auth with the demo credentials provided above.

### Quick Request Examples
Below are a few common requests you can try quickly in **Postman** or with **curl**.

```bash
# 1. Search jobs (public)
curl -X GET "{{base_url}}/api/jobs/search?title=developer" -H "accept: application/json"

# 2. Candidate login and store the session cookie (Postman handles this automatically)
curl -c cookies.txt -X POST "{{base_url}}/login" \
     -d "username=candidate1&password=password" \
     -H "Content-Type: application/x-www-form-urlencoded"

# 3. Apply for a job (replace 100 with an actual jobPostId)
curl -b cookies.txt -X POST "{{base_url}}/api/applications/apply?jobPostId=100"

# 4. Get my applications
curl -b cookies.txt -X GET "{{base_url}}/api/candidate/applications" -H "accept: application/json"
```

💡 **Tip:** When using Postman, set `base_url` in your environment and drop these examples straight into a new request tab – everything will resolve automatically.

## 📁 Project Structure

\`\`\`
src/
├── main/
│   ├── java/com/intellihire/jobportal/
│   │   ├── config/              # Security and configuration
│   │   ├── controller/          # REST controllers
│   │   │   └── api/            # API controllers
│   │   ├── model/              # Entity classes
│   │   ├── repository/         # Data repositories
│   │   ├── service/            # Business logic
│   │   │   └── impl/          # Service implementations
│   │   └── IntelliHireJobPortalApplication.java
│   └── resources/
│       ├── templates/          # Thymeleaf templates
│       ├── static/            # CSS, JS, images
│       ├── application.properties
│       └── data.sql           # Initial data
└── test/                      # Test classes
\`\`\`

## 🎨 UI Features

### Responsive Design
- Mobile-friendly Bootstrap layout
- Adaptive navigation and forms
- Touch-friendly interface

### Modern UI Elements
- Gradient backgrounds and cards
- Font Awesome icons
- Hover effects and animations
- Professional color scheme

### User Experience
- Intuitive navigation
- Clear role-based dashboards
- Form validation and error handling
- Success/error notifications

## 🔒 Security Features
- **Spring Security** integration
- **Role-based access control** (RBAC)
- **Password encryption** using BCrypt
- **CSRF protection**
- **Session management**
- **Method-level security**

## 📊 Database Schema
### Inspecting Data in MySQL
Need to peek at what’s happening under the hood?  Below are the quickest ways to inspect or troubleshoot your local MySQL data.

1. **Login to MySQL CLI**
   ```bash
   mysql -u root -p
   ```
2. **Select the IntelliHire database**
   ```sql
   USE intellihire_db;
   ```
3. **List all tables**
   ```sql
   SHOW TABLES;
   ```
4. **View recent job applications**
   ```sql
   SELECT a.id,
          c.username  AS candidate,
          jp.title    AS job,
          a.status,
          a.applied_at
   FROM applications a
   JOIN users c       ON a.candidate_id = c.id
   JOIN job_posts jp  ON a.job_post_id = jp.id
   ORDER BY a.applied_at DESC
   LIMIT 10;
   ```
5. **Check pending applications count (handy for dashboards)**
   ```sql
   SELECT COUNT(*) FROM applications WHERE status = 'PENDING';
   ```

🧹 **Cleanup tip:** In dev, you can wipe the database quickly:
```sql
DROP DATABASE intellihire_db;
CREATE DATABASE intellihire_db;
```
(Then restart the app – the schema & demo data are re-created automatically.)


### Core Entities
- **Users** (Base class for all user types)
- **Companies** (Company information)
- **Job Categories** (Job classification)
- **Job Posts** (Job listings)
- **Applications** (Job applications)
- **Statistics** (Dashboard metrics)

### Relationships
- Users → Companies (Many-to-One for Admins)
- Job Posts → Companies (Many-to-One)
- Job Posts → Categories (Many-to-One)
- Applications → Candidates (Many-to-One)
- Applications → Job Posts (Many-to-One)

## 🚀 Deployment

### Local Development
1. Ensure MySQL is running
2. Update database credentials
3. Run the application
4. Access via http://localhost:8080

### Production Deployment
1. Update `application.properties` for production database
2. Build JAR: `mvn clean package`
3. Deploy JAR to server
4. Configure reverse proxy (Nginx/Apache)
5. Set up SSL certificate

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- **Email**: support@intellihire.com
- **Documentation**: Check this README
- **Issues**: Create GitHub issues for bugs

## 🔄 Version History

- **v1.0.0** - Initial release with core functionality
- **v1.1.0** - Added advanced search and filtering
- **v1.2.0** - Enhanced UI and user experience

---

**Happy Job Hunting with IntelliHire! 🎯**
>>>>>>> master
