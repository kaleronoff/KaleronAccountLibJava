# <img src="https://strivio.org/static/Assets/Kaleron/LogoDark.png" alt="Kaleron Logo" width="30" height="30"> KaleronAccountLibJava [![GitHub license](https://img.shields.io/badge/license-NPL-red.svg)](LICENSE) [![](https://jitpack.io/v/kaleronoff/KaleronAccountLibJava.svg)](https://jitpack.io/#kaleronoff/KaleronAccountLibJava)

**KaleronAccountLibJava is a Java library designed to interact with the Kaleron Account Link API. It provides a simple and powerful way to perform various account-related actions such as retrieving email, sending comments, reading comments, liking/disliking videos, and more.**

## Features

- Get Account Email: Fetch the email associated with the linked account.

- Send Comment: Post comments to a video.

- Read Comments: Retrieve comments for a specific video.

- Like Video: Toggle the like status of a video.

- Dislike Video: Toggle the dislike status of a video.

- Permission-Based: The library checks for permissions before performing actions.

## Installation

See [how to add the library into your project with the JitPack repository](https://www.jitpack.io/#kaleronoff/KaleronAccountLibJava/1.0.0/#howto)

### Error Handling

The library will throw errors for the following reasons:

- Invalid or missing permissions.

- API call failures (network issues, invalid responses).

- Invalid linkToken or video IDs.

### Permissions

Permissions must be included in the account to perform specific actions. The library will check the permissions before executing any action. If the account does not have the necessary permissions, an error will be thrown.

## License

This project is licensed under the NPL (v1) License - see the [LICENSE](LICENSE) file for details.
