from distutils.core import setup

setup(
    # Application name:
    name="servertools",

    # Version number (initial):
    version="0.1.0",

    # Application author details:
    author="",
    author_email="",

    # Packages
    packages=["servertools"],

    # Include additional files into the package
    include_package_data=True,

    #
    # license="LICENSE.txt",
    description="server tools.",

    # long_description=open("README.txt").read(),

    # Dependent packages (distributions)
    install_requires=[
        "mariadb==1.1.7",
        "bcrypt==4.0.1"
    ],
)
