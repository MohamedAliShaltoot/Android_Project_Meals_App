# 🍽️ FoodTrack

FoodTrack is a modern Android application that allows users to explore meals, discover recipes, and save their favorite dishes.  
The app delivers a smooth user experience with smart search, offline support, animations, and multiple user modes.

---

## 🚀 User Journey

1. When the app launches, a **Splash Screen** with a **Lottie Animation** is displayed.
2. The app checks whether the user has opened it before.
3. Based on this check:
   - Returning users are redirected directly to the **Home Screen**.
   - First-time users are navigated to the **Login Screen**.

---

## 🔐 Authentication & Access Modes

Users can:
- **Log in** with an existing account.
- **Sign up** and create a new account.
- Continue using the app in **Guest Mode** (limited access).

After a successful login or sign-up:
- The user is redirected to the **Home Screen**.
- A friendly **welcome message** is displayed.

---

## 🏠 Home Screen

The Home Screen acts as the main hub of the app and includes:
- **Daily Meal** section displaying:
  - Meal image
  - Meal name
  - Country of origin
- **Categories** to explore meals by type.
- A built-in **Search Bar** for quick access.
- Ability to open full details by tapping on any meal.

### 🌐 No Internet Connection
- A clear **animation** appears when the internet connection is lost.
- Once the connection is restored, meals and categories reload automatically.

---

## 🔍 Smart Search

Users can search for meals using:
- Meal name
- Country
- Category
- Ingredients

### Search Highlights
- Search works **while typing** (first-letter search).
- No need to press any confirmation button.
- Results appear instantly.
- If no matching data is found:
  - A friendly **empty-state animation** is shown.

---

## 🍲 Meal Details Screen

Each meal includes:
- Meal image
- Meal name
- Country
- Ingredients list
- Step-by-step preparation instructions
- Cooking video
- **Add to Favorites** button
- **Add to Calender** button

---

## ❤️ Favorites Screen

- Available only for **logged-in users**.
- Displays all meals added to favorites.
- Users can remove meals at any time.
- Actions such as adding or removing a meal trigger a **Snackbar** for feedback.

---
  ## 📭 Calender Screen

- Available only for **logged-in users**.
- Displays all meals added to Calender.
- Actions such as adding or removing a meal trigger a **Snackbar** for feedback.

### 📡 Offline Support
- Favorite meals remain accessible even without an internet connection.

---

## 👤 Profile Screen

The Profile Screen allows users to:
- View personal information:
  - Name
  - Profile picture
  - Email address
- Log out and return to the Login Screen.

---

## 👥 Guest Mode

Guest users can:
- Browse meals.
- View categories.
- Open meal details.

Guest users **cannot**:
- Add meals to favorites.

If a guest attempts to add a meal to favorites:
- The app prompts them to log in or create an account.

---

## 💾 Data Persistence

- Favorite meals are stored locally.
- After logging out and logging back in:
  - All favorite meals remain saved and accessible.
- The app ensures a smooth experience even with limited or no internet access.

---

## 🧭 Screens Overview

- Splash Screen  
- Login / Register Screen  
- Home Screen  
- Search Screen  
- Category Screen  
- Meal Details Screen  
- Favorites Screen  
- Profile Screen  

---

## 🛠️ Tech Stack

- **Android (Java)**
- **Firebase**
  - Authentication
  - Realtime Database
- **Room Database**
- **RxJava**
- **Lottie Animations**

---

## 📦 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/MohamedAliShaltoot/Android_Project_Meals_App.git
