# ============================================================
# Simple Makefile — ONLY COMPILE main sources (empty resource fix)
# ============================================================

MAIN_SRC = src/main/java
MAIN_RES = src/main/resources
MAIN_OUT = out/main
LIB_DIR  = lib

# Set your Main class here
MAIN_CLASS = com.library.App

# Build classpath
ifeq ("$(wildcard $(LIB_DIR))","")
    MAIN_CP = -cp "$(MAIN_OUT)"
else
    MAIN_CP = -cp "$(MAIN_OUT):$(LIB_DIR)/*"
endif

# Default target
all: compile

compile:
	@echo "Compiling main Java sources..."
	@mkdir -p $(MAIN_OUT)
	@find $(MAIN_SRC) -name "*.java" > main_sources.txt
	@javac $(MAIN_CP) -d $(MAIN_OUT) @main_sources.txt

	@echo "Copying resources..."
	@if [ -d "$(MAIN_RES)" ]; then \
		cp -r $(MAIN_RES)/. $(MAIN_OUT)/; \
	else \
		echo "No resources directory found."; \
	fi

	@echo "Compile complete."
	
run: compile
	@echo "Running application..."
	@java $(MAIN_CP) $(MAIN_CLASS)

clean:
	@rm -rf out main_sources.txt
	@echo "Clean complete."
