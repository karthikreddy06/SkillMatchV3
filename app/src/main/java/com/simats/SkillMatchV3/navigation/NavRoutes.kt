package com.simats.SkillMatchV3.navigation

object NavRoutes {

    // Splash / Auth flow
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val ENABLE_PERMISSIONS = "enable_permissions"
    const val CHOOSE_ROLE = "choose_role"
    const val LOGIN = "login"
    const val REGISTER = "register"

    // Main containers
    const val SEEKER_ROOT = "seeker_root"
    const val EMPLOYER_ROOT = "employer_root"

    // Bottom bar tabs (Seeker)
    const val SEEKER_HOME = "seeker_home"
    const val MAP = "map"
    const val JOBS = "jobs"
    const val CHAT = "chat"
    const val SEEKER_PROFILE = "seeker_profile"

    // Seeker Profile Routes
    const val SEEKER_SAVED_JOBS = "saved_jobs"
    const val SEEKER_APPLIED_JOBS = "applied_jobs"
    const val SEEKER_NOTIFICATIONS = "notifications"
    const val SEEKER_SETTINGS = "settings/seeker"

    // Employer Screens
    const val EMPLOYER_HOME = "employer_home"
    const val EMPLOYER_POST_JOB = "employer_post_job"
    const val EMPLOYER_APPLICANTS = "employer_applicants"
    const val EMPLOYER_CHAT = "employer_chat"
    const val EMPLOYER_PROFILE = "employer_profile"
    
    // Additional Employer Screens
    const val EMPLOYER_ACTIVE_JOBS = "employer_active_jobs"
    const val EMPLOYER_CANDIDATE_PROFILE = "candidate_profile"
    const val EMPLOYER_EDIT_PROFILE = "employer_edit_profile"
    const val EMPLOYER_NOTIFICATIONS = "employer_notifications"

    // Profile actions
    const val EDIT_PROFILE = "edit_profile"
    const val RESUME = "resume"
    const val LOCATION = "location"
    const val AI_RECOMMENDATIONS = "ai_recommendations"
    
    // Seeker Profile Management Screens
    const val ADD_SKILLS = "add_skills"
    const val UPLOAD_RESUME = "upload_resume"
    const val JOB_PREFERENCES = "job_preferences"
    const val SEARCH_JOBS = "search_jobs"
    
    // Settings & Support
    const val SETTINGS = "settings/{role}"
    const val HELP_SUPPORT = "help_support/{role}"
    const val REPORT_ISSUE = "report_issue"

    // Other
    const val SKILLS_SELECTION = "skills"
    const val JOB_DETAIL = "job_detail"
}
