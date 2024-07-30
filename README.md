# GIC Dictionary Translator

Standalone translator for GIC Data Dictionary

## Requirements

- Docker
- GIC access
- 

## Usage

### Set API token

1. Copy your `Personalized Access Token` from the `User Profile` tab in GIC.
2. Open a terminal and run 
```shell
export TOKEN=<your token here>
```
Don't share this token with anyone

### Run the script

In the directory where you cloned this repository, run: 
```shell
 docker run --rm -it \
 --network host \
 --env TOKEN=$TOKEN \
 -v $(pwd)/out:/out \
 -v $(pwd)/in:/in \
 lukesikinabch/gic-dictionary-json-translator:1.0.1
```

Brief explanation of the command:
- `docker run`: run a container
- `--rm`: remove the container once it's done running
- `-it`: run in interactive mode (simplifying a bit here)
- `--network host` use the host network. This should get docker to play nicely with your VPN
- `--env TOKEN=$TOKEN` we need to tell the docker container about your GIC API token so it can pull data
- `-v $(pwd)/out:/out` map the directory `out/` in this repository to `/out/` inside the docker container. This
makes things stored in there persist after the docker container has been run and removed.
- `-v $(pwd)/in:/in` same thing, but for `in/`
- `lukesikinabch/gic-dictionary-json-translator:1.0.1` this is the name of the image we're using to make this container.
Think of an image as a copy of an operating system, and a container as a physical computer running that operating system.