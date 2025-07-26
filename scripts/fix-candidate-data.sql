-- Fix candidate data if needed
UPDATE users SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Romlg6VrGnvbm6vwn8YrU.S2' 
WHERE username IN ('candidate1', 'candidate2');

-- Ensure candidates table has proper foreign key references
UPDATE candidates c 
JOIN users u ON c.user_id = u.id 
SET c.user_id = u.id 
WHERE u.username IN ('candidate1', 'candidate2');
