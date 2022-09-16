# Tool for visualization of agile metrics from GitHub

This tool is part of a study carried out in the scope of the Master's thesis in Information Systems Management at the Instituto Superior de Ciências do Trabalho e da Empresa - Instituto Universitário de Lisboa (ISCTE-IUL).

A dashboard, relevant for software development teams working according to agile methodologies, was developed from metrics extracted from the GitHub platform.

Here's a guide to using the tool.


# GitHub

First you need to define a Kanban board in GitHub for the repository.

The kanban board should have a basic template with the columns: To Do / In Progress / Done

Rules:
- The kanban board will be per repository
- The kanban board must specifically have as columns: To Do / In Progress / Done. You can change their name, but not add more
- The tasks on the board should be issue (cards are not considered, you must transform them to issue)
- Issue closed should be in the "Done" column and be closed "Close Issue"


1)  View your repositories.

![img.png](images/repositories.png)

2) Create new project (Kanban board) for the repository.

a.	Go to the "Projects" tab

b.	Option "Projects"

c.	Click on "New Project"

![img.png](images/project.png)

3) Enter the required data from the Board and click on "Create Project".

a.	Name of the Board

b.	Description

c.	Template: Select the "Basic Kanban" template.

![img.png](images/createproject.png)

![img.png](images/projectteemplate.png)


4) The frame will be created as follows.

![img.png](images/projectteste.png)

5) To create issues click on the "+", set card text and add (Add).

![img.png](images/projetoteste1.png)

6) After creation, you must convert the card to issue. Click on the three dots on the card and "Convert to issue".  

![img.png](images/convertissue.png)

7) Then a screen will open to select the project repository and click "Convert to issue"

![img.png](images/convertnoteissue.png)

8) It will look like this.

![img.png](images/card1.png)

9) To close issue, move to "Done" column.  Click on the issue and then on "Close issue".

![img.png](images/criadocard1.png)

Clicking on the issue -> will open the following option to close.

![img.png](images/closeissue.png)


# Giro (http://3.249.103.74/)

Link: http://3.249.103.74/

You need to create an access token on GitHub: https://docs.github.com/pt/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token.

1) Set the start settings by clicking on "Settings". 
![image](https://user-images.githubusercontent.com/93287789/190505938-c60d98c6-5efa-41fe-8dce-7c60abcd9dad.png)

2) Settings screen:
a.	Enter GitHub user name and token.

![image](https://user-images.githubusercontent.com/93287789/190506187-5feed308-6baa-4095-978d-8556f4442e03.png)

b.	Select the repository and the Kanban project.
![image](https://user-images.githubusercontent.com/93287789/190506393-f77f3a62-bbac-49d5-8599-85c23c269006.png)
![image](https://user-images.githubusercontent.com/93287789/190506405-9327a914-f489-43fa-ae18-62099b9f20f8.png)

c.	Select the frenquency to see the data: day, week or month.

![image](https://user-images.githubusercontent.com/93287789/190506841-ed2e7516-bce6-47d4-951b-8b1603fe92f3.png)

d.	Configure which column corresponds "To Do" and "In Progress".

![image](https://user-images.githubusercontent.com/93287789/190619574-165ece24-1eff-434b-9eb6-eac3057741e0.png)

e.	Define the order of the columns (From Done to Done)
![image](https://user-images.githubusercontent.com/93287789/190620087-62cd952c-9498-4956-a54d-44008d535e66.png)

f.	Select start and end date of the issues.
![image](https://user-images.githubusercontent.com/93287789/190620398-3e77d6e5-9778-487f-b982-906eee3931e3.png)

3)  The chart will show the result of the metrics.

a.	Issue x Column: Current quantity of issues per column

b.	CFD: graph showing the work progress 

c.	Throughput: Issues closed in the period

d.	Lead Time: Amount in days of "data close issue - data issue in To Do".

e.	Cycle Time: Amount in days of "data close issue - data issue in In Progress".


![image](https://user-images.githubusercontent.com/93287789/190621537-6ceddfeb-9253-47e3-8144-938982d8fc5b.png)



