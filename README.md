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

![image](https://user-images.githubusercontent.com/93287789/190499891-9652a8f9-ffae-4d6b-860e-5f4195bd0597.png)

2) Create new project (Kanban board) for the repository.

a.	Go to the "Projects" tab

b.	Option "Projects"

c.	Click on "New Project"
![image](https://user-images.githubusercontent.com/93287789/190500484-61a2ed4d-45e8-493f-a993-dba036318f0b.png)

3) Enter the required data from the Board and click on "Create Project".

a.	Name of the Board

b.	Description

c.	Template: Select the "Basic Kanban" template.

![image](https://user-images.githubusercontent.com/93287789/190501736-b8d543e1-8060-4a65-8bdf-72a362226b6c.png)

![image](https://user-images.githubusercontent.com/93287789/190501764-5806b31d-e06f-4273-a82a-711e6dd72a19.png)


4) The frame will be created as follows.
![image](https://user-images.githubusercontent.com/93287789/190502380-eb8128d4-b833-4841-808b-a901c395d382.png)

5) To create issues click on the "+", set card text and add (Add).

![image](https://user-images.githubusercontent.com/93287789/190502633-15a4637b-dc91-476f-9af2-909284d4ff54.png)

6) After creation, you must convert the card to issue. Click on the three dots on the card and "Convert to issue".  
![image](https://user-images.githubusercontent.com/93287789/190503132-ae060985-c515-4a41-96d2-af75f59b48d8.png)

7) Then a screen will open to select the project repository and click "Convert to issue"

![image](https://user-images.githubusercontent.com/93287789/190503868-91c2b9a1-2841-4504-a530-4397adea860b.png)

8) It will look like this.
![image](https://user-images.githubusercontent.com/93287789/190504133-1c075bf8-e5e3-423a-8913-5b5214651d7a.png)

9) To close issue, move to "Done" column.  Click on the issue and then on "Close issue".

![image](https://user-images.githubusercontent.com/93287789/190504389-7c0a4dd8-4cc7-4000-aeb1-fff891b4ed61.png)

Clicking on the issue -> will open the following option to close.

![image](https://user-images.githubusercontent.com/93287789/190504596-5b5d3eab-e254-48a8-9460-5be2bb0316c4.png)


# Tool

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





