# AI-Bot
An Atritifical Intelligence based chatbot app which learns from human interactions and grows.
As the user keeps interacting, the bot keeps transcript of each conversation and grows accordingly.


 Install & try the app: [Download APK](https://drive.google.com/file/d/1NzS22CWuP5iF0RQOytyMGUt_TKbALD7V/view)
 
 
 ## Screenshots


  
 <a href="https://user-images.githubusercontent.com/42529024/168420459-cc69c5be-cab5-40f4-bd4a-fa11967aaf73.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/168420459-cc69c5be-cab5-40f4-bd4a-fa11967aaf73.png" width="22%" />
 <span>&nbsp;</span>
 <a href="https://user-images.githubusercontent.com/42529024/168420497-252d4e7c-79d4-47aa-ae7e-228e03f8937b.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/168420497-252d4e7c-79d4-47aa-ae7e-228e03f8937b.png" width="22%" />
</a>
<span>&nbsp;</span>
<a href="https://user-images.githubusercontent.com/42529024/168420548-b1af980c-9fbd-4bf3-9eaf-cf70e71a1990.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/168420548-b1af980c-9fbd-4bf3-9eaf-cf70e71a1990.png" width="22%" />
</a>
<span>&nbsp;</span>
<a href="https://user-images.githubusercontent.com/42529024/168420581-a1b65a67-0c86-47ea-8c57-838e35bfecf5.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/168420581-a1b65a67-0c86-47ea-8c57-838e35bfecf5.png" width="22%" />
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
