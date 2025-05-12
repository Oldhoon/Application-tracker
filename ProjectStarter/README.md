# Co-op/internship search status tracker

## CPSC 210 personal project

### project description: 

This application is designed to help users efficiently manage their Co-op and internship application process.
Users will be prompted to input essential information, including the application date, company name, position
title, application requirements, and links to relevant websites **after** applying to the job. The system will 
allow users to store cover letters or notes specific to each job application. 
All submitted applications will be displayed in a comprehensive view, complete with status updates that are
color-coded for easy reference. for example: 
- In review -> *yellow*
- Rejected  -> *red*
- Interview pending -> *purple*
- Accepted  -> *green*

The application is intended for students, recent graduates, and job seekers like myself actively applying for 
co-op positions and internships. The project is personally significant as I will be entering the job market
this upcoming summer. I would like to be able to utilize my own app to better organize and streamline my
application process. 

## User Stories

- As a user, I want to be able to add a new job application to my status tracker
- As a user, I want to be able to remove a new job application from my status tracker 
- As a user, I want to be able to view a list of the applications I have added to my status tracker so far 
- As a user, I want the app to automatically switch the status to rejected if no updates are made for 2 weeks
- As a user, I want the app to print a congratulatory message if I change a status to accepted
- As a user, I want to be able to sort the list of applications by the order applied, Company Names, and position
- As a user, I want to be able to update details and make changes to the status or notes tied to each application 
- As a user, when I select the quit option from the application menu, I want to be reminded to save the list of applications to file and have the option to do so or not
- As a user, when I start the applicationTracker, I want to be given the option to load the applications list from file 

## Instructions for End User 
- You can generate the first required action related to the user story "adding a new job application to my status tracker" by clicking the **Add Application** button at the top left of the window. this will open a dialog where you can enter the company name, position title and current status of the application. 
- You can generate the second required action related to the user story "removing a new job application from my status tracker" by clicking the **remove** button next to each application entry. This will prompt the user to confirm the deletion and once they do the entry will be deleted. 
- You can add notes to each entry by double clicking the left mouse button on the entry. This will open a dialog where you can enter text and press the add note button. 
- You can locate my visual component by changing the status of any application to ACCEPTED. This will open a dialog with a congratulatory message with an image to celebrate your success. 
- You can save the state of my application by clicking File -> Save from the menu bar. 
- You can reload the state of my application by clicking File -> Load from the menu bar. 

## Phase 4: Task 3
- One improvement would be the coupling between the ApplicationManager and the UI components. While it was necessary for the UI to interact with the ApplicationManager class to retrieve and manipulate application data, this connection could be abstracted using an interface or a controller class to better separate responsibilities. For example, introducing an ApplicationController could help mediate between the model and UI layers, reducing direct dependencies. Another refactoring that could be done is in the ApplicationDialog and NotesDialog classes which are responsible for collecting and submitting user input for applications and notes respectively. If I had more time, I would extract shared dialog functionalities into an abstract superclass. This would eliminate reduncacy in my code and allow for consistent ui design. It would also make things easier if I decided to add on more dialog classes in the future. 