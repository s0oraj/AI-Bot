# AI-Bot
An Atritifical Intelligence based chatbot app which learns from human interactions and grows.
As the user keeps interacting, the bot keeps transcript of each conversation and grows accordingly.


 Install & try the app: [Download APK](https://drive.google.com/file/d/1NzS22CWuP5iF0RQOytyMGUt_TKbALD7V/view)
 
 
 ## Screenshots
  
 <a href="https://user-images.githubusercontent.com/42529024/169632818-965e5769-1672-4480-8c54-1f3d682f5cb3.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/169632818-965e5769-1672-4480-8c54-1f3d682f5cb3.png" width="22%" />
 <span>&nbsp;</span>
 <a href="https://user-images.githubusercontent.com/42529024/169632978-92850aad-4b24-465f-a00a-4b6c332d0360.png" target="_blank">
  <img src="https://user-images.githubusercontent.com/42529024/169632978-92850aad-4b24-465f-a00a-4b6c332d0360.png" width="22%" />
</a>


## Working of the app
 
 * This app displays a list of user and bot messages combined using androids recyclerview and recyclerview adapter classes.
 * It fetches data from an [PersonalityForge API](https://www.personalityforge.com/chatbot-api.php). Data is being fetched by using  [Volley library](https://github.com/google/volley) to connect to the internet. Volley works in the background thread and once the response is out, it then returns this response to the main UI thread from where the new data is shown in the Main Screen of the app.

 ## Features
 * Users can interact with an AI bot and as they talk more and more, the bot adapts and grows. It uses all conversation transcripts with any user.
 
