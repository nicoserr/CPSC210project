# Notetaking Application

## What does it do?

This is an application that will allow the user to organize their notes. There will be three categories:
- **Subject** 
    - Examples of subjects are: *Computer Science*, *Physics*, *Philosophy*
- **Course** 
    - For Computer Science, examples of courses are: *CPSC210*, *CPSC110*, *CPSC213*
- **Topic** 
    - For CPSC210, examples of topics are: *Type Hierarchy*, *Overloading*, *Overriding*
    
Each of these categories is a subcategories of the one above it. This means that within the subject of 
*Computer Science* we may find various courses (as shown above) and within these courses we find various topics.

The actual notes will be the topics. That is to say, each topic contains a document in which the user may type or
copy/paste their notes. These may be viewed and/or edited later on. However, only **Topics** have this quality, a 
Subject only contains Courses and a Course only contains Topics.

The user will be able to add any amount of Subjects, Courses or Topics as well as delete already existing ones, allowing
them to personalize the application.


## Who will use it?

Any kind of student will find the application useful. It gives students who struggle with notetaking or organizing their
notes a simple and efficient tool that keeps their notes organized. Furthermore, having these organized notes means that
not only notetaking becomes easier, but so does studying. Students will have the ability to revisit their notes from
previous topics and courses in order to study for upcoming exams and with the organization the app provides, they'll be
able to study more efficiently.

I believe that it will be particularly useful for students in High School and University, since notetaking and studying
become much more important.

## Why is this project of interest to me?

This project implements the method of notetaking that I have developed over the years. I take notes divided by topic and
have notebooks for each course I take. This project would digitalize what I have done and makes it much simpler to
look at previous notes and study for exams.

If the project turns out great, I might use it for my actual notetaking.

## User Stories
- As a user, I want to be able to add a new Subject to my list of Subjects.
- As a user, I want to be able to add a new Course to a Subject's list of Courses.
- As a user, I want to be able to add a new Topic to a Course's list of Topics.
- As a user, I want to be able to remove a Subject, Course or Topic from their respective lists.
- As a user, I want to be able to select a Subject and view a list of the Courses for that Subject.
- As a user, I want to be able to select a Course and view a list of the Topics for that Course.
- As a user, I want to be able to save my Courses, Subjects and Topics to file
- As a user, I want to be able to load my Courses, Subjects and Topics from file
- As a user, I want to be able to collapse all folders of Courses and Subjects 
- As a user, I want to be able to expand all folders of Courses and Subjects

## Phase 4: Task 2
I believe all my classes and methods have robust design. There are no requires clauses anywhere in my code. More
specifically, Subject, Course, Topic and NotetakingAppTree are the classes that contain methods that throw exceptions.
These are all caught in the NotetakingAppGUI class.

There are two exceptions: EmptyListException and InvalidAdditionException. The EmptyListException is used in the
retrieveSubject, retrieveCourse and retrieveTopic methods (for the GUI) when the list of subjects, courses or topics
from which the element will be retrieved is empty. This exception had more use in the console UI since it was thrown and
caught in the printing of the menu, since when the subjects, courses or topics were empty, the user was notified.

The second exception (InvalidAdditionException) has two uses. The first one was when there was an attempt to create
a Subject, Course or Topic with an empty name. This exception was thrown from the constructor of each respective class
and propagated up until the tryAddCourse method for the console UI and to the tryAddObject method in the GUI. The 
second use was when the user tried to add an object to a Topic. This use was from within the addObject method in 
NotetakingAppTree and only applied to the GUI.