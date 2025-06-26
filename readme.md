[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />

<h3 align="center">Marvel Java Spring Boot API</h3>

<p align="center">
  Java Spring Boot REST API for Marvel universe data.
  <br />
  <a href="https://github.com/necmettincimen/marvel-java-spring-boot"><strong>Explore the docs »</strong></a>
  <br />
  <br />
  <a href="https://github.com/necmettincimen/marvel-java-spring-boot/issues">Report Bug</a>
  ·
  <a href="https://github.com/necmettincimen/marvel-java-spring-boot/issues">Request Feature</a>
</p>

---

## Table of Contents

- [About The Project](#about-the-project)
  - [Built With](#built-with)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)
- [Acknowledgements](#acknowledgements)

---

## About The Project

This project is a RESTful API built with Java Spring Boot that provides access to Marvel universe data such as characters, comics, and stories. It is designed for learning, demo, and integration purposes.

### Built With

- [Java 17+](https://adoptium.net/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [H2 Database](https://www.h2database.com/) (for development/testing)
- [Lombok](https://projectlombok.org/)

---

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

- Java 17 or newer
- Maven 3.8+

### Installation

1. Clone the repository
   ```sh
   git clone https://github.com/necmettincimen/marvel-java-spring-boot.git
   ```
2. Go to the project directory
   ```sh
   cd marvel-java-spring-boot
   ```
3. Build the project
   ```sh
   mvn clean install
   ```
4. Run the application
   ```sh
   mvn spring-boot:run
   ```

---

## Usage

Once the application is running, you can access the API at `http://localhost:8080/api`.

Example endpoints:
- `GET /api/characters` - List all Marvel characters
- `GET /api/comics` - List all comics
- `GET /api/characters/{id}` - Get character details

API documentation (Swagger UI) is available at `http://localhost:8080/swagger-ui.html`.

---

## Roadmap

- [ ] Add authentication and authorization
- [ ] Integrate with external Marvel API
- [ ] Add Docker support
- [ ] Improve test coverage

See the [open issues](https://github.com/necmettincimen/marvel-java-spring-boot/issues) for more.

---

## Contributing

Contributions are welcome! Please fork the repo and submit a pull request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## License

Distributed under the MIT License. See `LICENSE` for more information.

---

## Contact

Necmettin Çimen - [@Necmettin Cimen](https://necmettincimen.github.io) - [necmettin.dev@gmail.com](mailto:necmettin.dev@gmail.com)

Project Link: [https://github.com/necmettincimen/marvel-java-spring-boot](https://github.com/necmettincimen/marvel-java-spring-boot)

---

## Acknowledgements

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Marvel Developer Portal](https://developer.marvel.com/)
- [Lombok](https://projectlombok.org/)
- [H2 Database](https://www.h2database.com/)

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/necmettincimen/marvel-java-spring-boot.svg?style=for-the-badge
[contributors-url]: https://github.com/necmettincimen/marvel-java-spring-boot/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/necmettincimen/marvel-java-spring-boot.svg?style=for-the-badge
[forks-url]: https://github.com/necmettincimen/marvel-java-spring-boot/network/members
[stars-shield]: https://img.shields.io/github/stars/necmettincimen/marvel-java-spring-boot.svg?style=for-the-badge
[stars-url]: https://github.com/necmettincimen/marvel-java-spring-boot/stargazers
[issues-shield]: https://img.shields.io/github/issues/necmettincimen/marvel-java-spring-boot.svg?style=for-the-badge
[issues-url]: https://github.com/necmettincimen/marvel-java-spring-boot/issues
[license-shield]: https://img.shields.io/github/license/necmettincimen/marvel-java-spring-boot.svg?style=for-the-badge
[license-url]: https://github.com/necmettincimen/marvel-java-spring-boot/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/necmettincimen
