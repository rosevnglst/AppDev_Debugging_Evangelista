**A.** I found that when "Remember me" is checked, the email is saved in SharedPreferences. The original logout function only clears the session from memory, leaving the saved email behind. This causes the app to automatically log me back in after restarting.

**B.** Before logging in, I expect to see no saved email in the XML file. After logging in with "Remember me" checked, the email is saved. Based on the original code, the email remains in the XML file even after I log out.

**C.** I fixed the issue in `SessionManager.java` by adding a line of code that removes the saved email from SharedPreferences when logging out. This ensures that both the temporary and saved sessions are cleared without changing anything in `HomeActivity.java`.
