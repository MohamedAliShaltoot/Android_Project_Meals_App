# FoodTrack

FoodTrack is an Android mobile application that helps users explore, organize, and manage their meals. Users can search for recipes, save their favorites, and create a weekly meal plan. The app offers different functionalities depending on whether the user is logged in or using the guest mode.

---

## Features

- **User Authentication**
  - Sign up with a new email or log in with an existing account.
  - Guest mode available (limited access).

- **Home Screen**
  - Daily meal suggestions are displayed in a card view.
  - Categories section to explore meals by type.
  - Quick access to search functionality.
  - Tap on any meal to view details:
    - Full name
    - Origin
    - Images
    - Ingredients
    - Preparation steps
    - Instructional video

- **Search Screen**
  - Users can search for meals by:
    - Country
    - Category
    - Ingredients
  - Users must select a filter to perform a search.
  - Displays all relevant results in a list.
  - Tap on a meal to view full details.

- **Favourite Screen**
  - Logged-in users can save meals to favorites.
  - Favorites displayed in card view with meal name, image, and a heart icon.
  - Remove meals from favorites by:
    - Tapping the red heart icon
    - Swiping the card to the left
  - Confirmation messages appear when a meal is removed.

- **Profile Screen**
  - Displays user profile picture, name, and email.
  - Log out option with confirmation prompt.

- **Guest Mode Limitations**
  - Guests can view meals and search for recipes.
  - Cannot add meals to favorites or create a weekly meal plan.

---

## Screens

1. **HomeScreen**
2. **FavouriteScreen**
3. **SearchScreen**
4. **ProfileScreen**

---

## How It Works

1. Users open the app and choose to sign up, log in, or use guest mode.
2. In the HomeScreen:
   - Users can explore daily meals and categories.
   - Tap a meal to see detailed information and video tutorials.
   - Use the search bar to find specific meals using filters.
3. In the FavouriteScreen:
   - Logged-in users manage their favorite meals with easy add/remove functionality.
4. In the ProfileScreen:
   - Users can view account info and log out safely.
5. Guest users can browse meals but cannot save favorites or create meal plans.

---

## Tech Stack

- Frontend: Android / Java
- Backend: Firebase (Authentication & Database)
- Local Database: Room Database


---

## Installation

1. Clone the repository:
   ```bash
   [git clone https://github.com/yourusername/FoodTrack.git](https://github.com/MohamedAliShaltoot/Android_Project_Meals_App.git)
