# Quizzy 📚

A modern Android quiz application built with Kotlin, featuring an engaging user interface with animations and real-time feedback.

## 🎯 Features

- **Interactive Quiz Experience**: Multiple-choice questions with smooth animations
- **Progress Tracking**: Visual progress bar showing quiz completion
- **Streak System**: Tracks consecutive correct answers with fire animations
- **Real-time Feedback**: Immediate visual feedback for correct/incorrect answers
- **Modern UI**: Material Design 3 with beautiful animations and transitions
- **Network Integration**: Fetches quiz questions from external API
- **Responsive Design**: Optimized for different screen sizes and orientations

## 🏗️ Architecture

This project follows modern Android development best practices:

- **MVVM Architecture**: Clean separation of concerns with ViewModels
- **Dependency Injection**: Hilt for dependency management
- **Navigation Component**: Type-safe navigation between fragments
- **Retrofit**: Network calls for fetching quiz data
- **Coroutines**: Asynchronous programming for smooth UI
- **ViewBinding**: Type-safe view access

### Project Structure

```
app/src/main/java/com/project/quizzy/
├── data/
│   ├── model/
│   │   └── Question.kt          # Data model for quiz questions
│   └── repo/
│       └── QuizRepository.kt     # Repository pattern implementation
├── di/
│   └── AppModule.kt             # Hilt dependency injection module
├── network/
│   ├── QuizApiService.kt        # Retrofit API interface
│   └── RetroFitInstance.kt      # Retrofit configuration
├── ui/
│   ├── MainActivity.kt          # Main activity
│   ├── quiz/
│   │   └── QuizFragment.kt      # Quiz gameplay screen
│   ├── result/
│   │   └── ResultFragment.kt    # Results screen
│   ├── splash/
│   │   └── SplashFragment.kt    # Splash screen
│   └── QuizApp.kt               # Application class
└── viewmodel/
    ├── QuizUiState.kt           # UI state management
    └── QuizViewModel.kt         # Quiz business logic
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 24 (API level 24) or higher
- Kotlin 1.8+
- Gradle 8.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/quizzy.git
   cd quizzy
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Sync and Build**
   - Wait for Gradle sync to complete
   - Build the project (Build → Make Project)

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon) or press Shift+F10

### Configuration

The app is configured to work with a quiz API. You may need to update the API endpoint in `RetroFitInstance.kt` to point to your quiz data source.

## 🎮 How to Play

1. **Start the Quiz**: Launch the app and wait for the splash screen
2. **Answer Questions**: Tap on one of the four answer options
3. **Get Feedback**: See immediate visual feedback for your answer
4. **Track Progress**: Monitor your progress through the progress bar
5. **Build Streaks**: Get consecutive correct answers to see the fire animation
6. **View Results**: See your final score and performance at the end

## 🛠️ Technologies Used

- **Kotlin**: Primary programming language
- **Android Jetpack Components**:
  - Navigation Component
  - ViewModel
  - LiveData
  - ViewBinding
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + Gson
- **UI/UX**: Material Design 3, Lottie animations
- **Architecture**: MVVM pattern
- **Build System**: Gradle with Kotlin DSL

## 📱 Screenshots

*[Screenshots would be added here]*

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Material Design 3 for the beautiful UI components
- Lottie for the smooth animations
- Retrofit for seamless network communication
- Hilt for dependency injection

## 📞 Support

If you encounter any issues or have questions, please:

1. Check the existing issues in the GitHub repository
2. Create a new issue with detailed information about your problem
3. Include device information, Android version, and steps to reproduce

---

**Made with ❤️ for the Android community** 