# This makefile makes it easier to use vim.

all:
	./mvnw compile | \
		grep '\[ERROR\] [^[:space:]]\{1,\}\.java' | \
		sed -e 's|^\[[^]]\{1,\}\] ||g' \
		-e 's|^\([^:]\{1,\}\):.\([0-9]\{1,\}\),\([0-9]\{1,\}\).|\1:\2:\3|g'

check:
	./mvnw checkstyle:checkstyle | \
		grep '\[ERROR\] src/' | \
		sed -e 's|^\[[^]]\{1,\}\] ||g' \
		-e 's|^\([^:]\{1,\}\):.\([0-9]\{1,\}\),\([0-9]\{1,\}\).|\1:\2:\3|g'



format:
	./mvnw formatter:format
