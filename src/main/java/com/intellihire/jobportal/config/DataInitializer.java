package com.intellihire.jobportal.config;

import com.intellihire.jobportal.model.*;
import com.intellihire.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private UserService userService;

  @Autowired
  private CompanyService companyService;

  @Autowired
  private JobCategoryService jobCategoryService;

  @Autowired
  private JobPostService jobPostService;

  @Autowired
  private CandidateService candidateService;

  @Override
  public void run(String... args) throws Exception {
      try {
          initializeData();
      } catch (Exception e) {
          System.err.println("⚠️  Data initialization failed: " + e.getMessage());
          // Don't throw exception to prevent application startup failure
      }
  }

  private void initializeData() {
      // With spring.jpa.hibernate.ddl-auto=create-drop, the database is dropped and recreated
      // on every startup, so we don't need to check for existing users.
      System.out.println("🚀 Initializing sample data...");

      // Create Companies first
      Company techCorp = new Company("TechCorp Solutions", 
          "Leading technology solutions provider specializing in enterprise software development", 
          "https://techcorp.com", 
          "123 Tech Street, Silicon Valley, CA 94000", 
          "+1-555-0101");
      techCorp = companyService.saveCompany(techCorp);

      Company innovateHub = new Company("InnovateHub", 
          "Innovation and startup incubator helping businesses transform digitally", 
          "https://innovatehub.com", 
          "456 Innovation Ave, Austin, TX 78701", 
          "+1-555-0102");
      innovateHub = companyService.saveCompany(innovateHub);

      Company globalSoft = new Company("GlobalSoft Inc", 
          "Global software development company with offices worldwide", 
          "https://globalsoft.com", 
          "789 Software Blvd, Seattle, WA 98101", 
          "+1-555-0103");
      globalSoft = companyService.saveCompany(globalSoft);

      // Create Job Categories
      JobCategory softwareDev = jobCategoryService.saveJobCategory(
          new JobCategory("Software Development", "Programming, software engineering, and development roles"));
      
      JobCategory dataScience = jobCategoryService.saveJobCategory(
          new JobCategory("Data Science", "Data analysis, machine learning, AI, and analytics roles"));
      
      JobCategory productMgmt = jobCategoryService.saveJobCategory(
          new JobCategory("Product Management", "Product strategy, management, and planning positions"));
      
      JobCategory design = jobCategoryService.saveJobCategory(
          new JobCategory("Design", "UI/UX design, graphic design, and creative positions"));
      
      JobCategory marketing = jobCategoryService.saveJobCategory(
          new JobCategory("Marketing", "Digital marketing, content, and growth roles"));

      JobCategory devops = jobCategoryService.saveJobCategory(
          new JobCategory("DevOps", "Infrastructure, deployment, and operations roles"));

      // Create Admin User
      Admin admin = new Admin("admin", "admin@intellihire.com", "password", 
          "System", "Administrator", techCorp, "IT Administration", "System Administrator");
      admin = (Admin) userService.saveUser(admin);

      // Create HR Manager (Employee role)
      User hrManager = new User("hr_manager", "hr@techcorp.com", "password", 
          "John", "Smith", Role.EMPLOYEE);
      hrManager = userService.saveUser(hrManager);

      // Create another HR Manager for different company
      User hrManager2 = new User("hr_manager2", "hr2@innovatehub.com", "password", 
          "Sarah", "Johnson", Role.EMPLOYEE);
      hrManager2 = userService.saveUser(hrManager2);

      // Create Candidates with proper password encoding
      Candidate candidate1 = new Candidate("candidate1", "candidate@example.com", "password", 
          "Jane", "Doe");
      candidate1.setPhone("+1-555-0201");
      candidate1.setAddress("123 Main St, Anytown, USA 12345");
      candidate1.setSkills("Java, Spring Boot, React, MySQL, Docker");
      candidate1.setExperience("3 years of full-stack software development experience");
      candidate1.setEducation("Bachelor of Computer Science - University of Technology");
      candidate1.setDateOfBirth(LocalDate.of(1995, 6, 15));
      // Use userService to save candidate with encoded password
      candidate1 = (Candidate) userService.saveUser(candidate1);

      Candidate candidate2 = new Candidate("candidate2", "candidate2@example.com", "password", 
          "Mike", "Wilson");
      candidate2.setPhone("+1-555-0202");
      candidate2.setAddress("456 Oak Ave, Tech City, USA 54321");
      candidate2.setSkills("Python, Django, PostgreSQL, AWS, Machine Learning");
      candidate2.setExperience("5 years of backend development and data analysis");
      candidate2.setEducation("Master of Computer Science - Tech University");
      candidate2.setDateOfBirth(LocalDate.of(1992, 3, 22));
      // Use userService to save candidate with encoded password
      candidate2 = (Candidate) userService.saveUser(candidate2);

      // Create Job Posts
      JobPost job1 = new JobPost();
      job1.setTitle("Senior Java Developer");
      job1.setDescription("We are looking for an experienced Java developer to join our dynamic team. You will be responsible for developing high-quality applications and working with cross-functional teams to deliver exceptional software solutions.");
      job1.setRequirements("Java 8+, Spring Boot, Microservices, REST APIs, MySQL, 5+ years experience, Strong problem-solving skills");
      job1.setLocation("San Francisco, CA");
      job1.setJobType(JobType.FULL_TIME);
      job1.setExperienceLevel(ExperienceLevel.SENIOR_LEVEL);
      job1.setMinSalary(new BigDecimal("80000"));
      job1.setMaxSalary(new BigDecimal("120000"));
      job1.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job1.setCompany(techCorp);
      job1.setCategory(softwareDev);
      job1.setPostedBy(hrManager);
      jobPostService.saveJobPost(job1);

      JobPost job2 = new JobPost();
      job2.setTitle("Data Scientist");
      job2.setDescription("Join our data science team to work on cutting-edge machine learning projects. You will analyze large datasets, build predictive models, and help drive data-driven decision making across the organization.");
      job2.setRequirements("Python, Machine Learning, Statistics, SQL, TensorFlow/PyTorch, PhD preferred, 3+ years experience");
      job2.setLocation("Remote");
      job2.setJobType(JobType.FULL_TIME);
      job2.setExperienceLevel(ExperienceLevel.MID_LEVEL);
      job2.setMinSalary(new BigDecimal("90000"));
      job2.setMaxSalary(new BigDecimal("140000"));
      job2.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job2.setCompany(innovateHub);
      job2.setCategory(dataScience);
      job2.setPostedBy(hrManager2);
      jobPostService.saveJobPost(job2);

      JobPost job3 = new JobPost();
      job3.setTitle("Frontend Developer");
      job3.setDescription("Build amazing user interfaces with React and modern web technologies. Work closely with designers and backend developers to create seamless user experiences.");
      job3.setRequirements("React, JavaScript, HTML5, CSS3, TypeScript, 2+ years experience, Portfolio required");
      job3.setLocation("Austin, TX");
      job3.setJobType(JobType.FULL_TIME);
      job3.setExperienceLevel(ExperienceLevel.MID_LEVEL);
      job3.setMinSalary(new BigDecimal("60000"));
      job3.setMaxSalary(new BigDecimal("90000"));
      job3.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job3.setCompany(globalSoft);
      job3.setCategory(softwareDev);
      job3.setPostedBy(hrManager);
      jobPostService.saveJobPost(job3);

      JobPost job4 = new JobPost();
      job4.setTitle("Product Manager");
      job4.setDescription("Lead product strategy and development for our flagship products. Work with engineering, design, and marketing teams to deliver products that customers love.");
      job4.setRequirements("Product Management experience, Agile/Scrum, Market research, Analytics, MBA preferred");
      job4.setLocation("New York, NY");
      job4.setJobType(JobType.FULL_TIME);
      job4.setExperienceLevel(ExperienceLevel.SENIOR_LEVEL);
      job4.setMinSalary(new BigDecimal("100000"));
      job4.setMaxSalary(new BigDecimal("150000"));
      job4.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job4.setCompany(techCorp);
      job4.setCategory(productMgmt);
      job4.setPostedBy(hrManager);
      jobPostService.saveJobPost(job4);

      JobPost job5 = new JobPost();
      job5.setTitle("UX/UI Designer");
      job5.setDescription("Create intuitive and beautiful user experiences for our web and mobile applications. Collaborate with product and engineering teams to bring designs to life.");
      job5.setRequirements("Figma, Sketch, Adobe Creative Suite, User Research, Prototyping, 3+ years experience");
      job5.setLocation("Seattle, WA");
      job5.setJobType(JobType.FULL_TIME);
      job5.setExperienceLevel(ExperienceLevel.MID_LEVEL);
      job5.setMinSalary(new BigDecimal("70000"));
      job5.setMaxSalary(new BigDecimal("100000"));
      job5.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job5.setCompany(globalSoft);
      job5.setCategory(design);
      job5.setPostedBy(hrManager);
      jobPostService.saveJobPost(job5);

      JobPost job6 = new JobPost();
      job6.setTitle("DevOps Engineer");
      job6.setDescription("Build and maintain our cloud infrastructure. Implement CI/CD pipelines and ensure high availability and scalability of our systems.");
      job6.setRequirements("AWS/Azure, Docker, Kubernetes, Jenkins, Terraform, Linux, 4+ years experience");
      job6.setLocation("San Francisco, CA");
      job6.setJobType(JobType.FULL_TIME);
      job6.setExperienceLevel(ExperienceLevel.SENIOR_LEVEL);
      job6.setMinSalary(new BigDecimal("95000"));
      job6.setMaxSalary(new BigDecimal("130000"));
      job6.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job6.setCompany(techCorp);
      job6.setCategory(devops);
      job6.setPostedBy(hrManager);
      jobPostService.saveJobPost(job6);

      JobPost job7 = new JobPost();
      job7.setTitle("Marketing Specialist");
      job7.setDescription("Drive growth through digital marketing campaigns. Manage social media, content marketing, and lead generation initiatives.");
      job7.setRequirements("Digital Marketing, SEO/SEM, Social Media, Content Creation, Analytics, 2+ years experience");
      job7.setLocation("Remote");
      job7.setJobType(JobType.FULL_TIME);
      job7.setExperienceLevel(ExperienceLevel.MID_LEVEL);
      job7.setMinSalary(new BigDecimal("50000"));
      job7.setMaxSalary(new BigDecimal("75000"));
      job7.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job7.setCompany(innovateHub);
      job7.setCategory(marketing);
      job7.setPostedBy(hrManager2);
      jobPostService.saveJobPost(job7);

      JobPost job8 = new JobPost();
      job8.setTitle("Software Engineer Intern");
      job8.setDescription("Join our team as an intern and gain hands-on experience in software development. Work on real projects and learn from experienced developers.");
      job8.setRequirements("Computer Science student, Basic programming knowledge, Eager to learn, Good communication skills");
      job8.setLocation("Austin, TX");
      job8.setJobType(JobType.INTERNSHIP);
      job8.setExperienceLevel(ExperienceLevel.ENTRY_LEVEL);
      job8.setMinSalary(new BigDecimal("20000"));
      job8.setMaxSalary(new BigDecimal("30000"));
      job8.setApplicationDeadline(LocalDateTime.of(2024, 12, 31, 23, 59, 59));
      job8.setCompany(innovateHub);
      job8.setCategory(softwareDev);
      job8.setPostedBy(hrManager2);
      jobPostService.saveJobPost(job8);

      System.out.println("✅ Sample data initialized successfully!");
      System.out.println("🔐 Demo Credentials (all passwords are 'password'):");
      System.out.println("   👨‍💼 Admin: admin / password");
      System.out.println("   👩‍💼 HR Manager: hr_manager / password");
      System.out.println("   👩‍💼 HR Manager 2: hr_manager2 / password");
      System.out.println("   👤 Candidate 1: candidate1 / password");
      System.out.println("   👤 Candidate 2: candidate2 / password");
  }
}
