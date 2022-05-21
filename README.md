# AI-Bot
An Atritifical Intelligence based chatbot app which learns from human interactions and grows.
As the user keeps interacting, the bot keeps transcript of each conversation and grows accordingly.


 Install & try the app: [Download APK](https://drive.google.com/file/d/1NzS22CWuP5iF0RQOytyMGUt_TKbALD7V/view)
 
 
 ## Screenshots


  
 <a href="https://user-images.githubusercontent.com/42529024/169632818-965e5769-1672-4480-8c54-1f3d682f5cb3.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/169632818-965e5769-1672-4480-8c54-1f3d682f5cb3.png" width="22%" />
 <span>&nbsp;</span>
 <a href="https://user-images.githubusercontent.com/42529024/169632844-b9cc2755-e5d7-4df6-bb60-fa37ef98f720.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/169632844-b9cc2755-e5d7-4df6-bb60-fa37ef98f720.png" width="22%" />
</a>


## Working of the app
 
 * This app displays a list of students academic information in its main ui screen.
 * It fetches data from an [SQLite](https://github.com/sqlite/sqlite) database via a [ContentProvider](https://developer.android.com/guide/topics/providers/content-providers).
 * The data is recieved in the form of a [Cursor](https://developer.android.com/reference/android/database/Cursor). The main ui screen is updated by a [CursorAdapter](https://developer.android.com/reference/android/widget/CursorAdapter) which uses this cursor as its dataset.
 
 ## Features
 * Users can add a student data such as name, roll number, branch and semester into the app. 
 * This user entered data gets stored by android in the SQLite database.
 * The data is conserved even after the app is being closed. Therefore users can refer to the student data later.
 * Existing student data can be updated or deleted as desired by the specific user using the app.
