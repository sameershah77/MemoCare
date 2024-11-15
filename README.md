# SilentEcho - Android App for Breast Cancer Detection

**SilentEcho** is an Android application designed to assist in breast cancer detection using advanced AI and image processing techniques. The app utilizes a CNN (Convolutional Neural Network) model for cancer detection and allows users to scan mammographic images via their camera. It provides insightful visualizations, such as a pie chart that differentiates between malignant and benign cases, helping doctors and radiologists make informed decisions.

---

## Features

- **Mammogram Image Scanning**: Use the camera to scan mammogram images for analysis.
- **AI-Powered Cancer Detection**: The app uses a pre-trained CNN model to detect signs of cancer from the scanned images.
- **Visualization with Pie Chart**: Provides a pie chart visualization to show the distribution of malignant and benign cases, helping in quick diagnosis.
- **Bottom Navigation Bar**: Offers easy navigation between different sections of the app, such as the scan screen and the chat functionality.
- **AI Assistance Integration**: Uses retrofitted AI assistance to enhance the diagnosis and decision-making process.
  
---

## App Structure

The application is designed with a **bottom navigation** layout for easy navigation between various sections:

1. **Scan Fragment**: Where the user can scan mammogram images.
2. **Chat Fragment**: A feature for users to interact with AI-powered assistance or communicate with medical professionals.

---

## Tech Stack

- **Android**: Kotlin-based Android application.
- **AI/ML**: Convolutional Neural Network (CNN) for cancer detection integrated into the app.
- **Shared Preferences**: Used to store user data and preferences (like first-time login).
- **Bottom Navigation**: Utilized for easy switching between scan and chat functionalities.
- **Retrofit**: Used for integrating AI assistance and communication with external APIs for data processing and diagnosis.

---

## Installation

1. Clone the repository or download the project as a ZIP file.
2. Open the project in **Android Studio**.
3. Build the project to sync the dependencies.
4. Run the app on an Android emulator or physical device.

---

## Usage

1. **Launch the app**.
2. **Navigate through the bottom navigation bar**:
   - Click on the **Scan** option to scan a mammogram image using your device's camera.
   - The app will process the image and analyze it using the CNN model.
   - It will display the cancer diagnosis result and provide a visualization using a pie chart to show malignant vs benign proportions.
   - Switch to the **Chat** option for AI assistance and guidance.

---

## Data Privacy

This app processes medical images for cancer detection, but **no personal data** is collected, stored, or shared with any external parties without the user's consent. The app focuses only on the medical analysis and visual presentation.

---

## Contributing

Contributions to this project are welcome. Feel free to open an issue or submit a pull request if you have suggestions or improvements.

---

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

---

For more information or support, please contact us at:  
**Email**: support@silentecho.com
