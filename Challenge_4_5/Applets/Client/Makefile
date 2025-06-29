# Applet and package metadata
APPLET_AID      = 0xCC:0xCC:0xCC:0xCC:0xCC:0x1
APPLET_NAME     = clientPackage.ClientApplet
PACKAGE_AID     = 0xCC:0xCC:0xCC:0xCC:0xCC
PACKAGE_NAME    = clientPackage
PACKAGE_VERSION = 1.0

# List of source files
SOURCES = \
    src/clientPackage/ClientApplet.java

# Output directories
BUILD_DIR           = ./build
BUILD_CLASSES_DIR   = $(BUILD_DIR)/classes
BUILD_JAVACARD_DIR  = $(BUILD_DIR)/javacard

# Tools and paths
JAVA_HOME           = /usr/lib/jvm/java-21-openjdk-amd64
JAVACARD_SDK_DIR    ?= ../../SDKs/jc32_kit
JAVACARD_EXPORT_DIR ?= $(JAVACARD_SDK_DIR)/api_export_files:/home/user/Working-Dir/JC_All/Applets/Server/build/javacard/
CLASSPATH           = $(JAVACARD_SDK_DIR)/lib/api_classic-3.2.0.jar

JFLAGS              = -target 1.8 -source 1.8 -g -d $(BUILD_CLASSES_DIR) -classpath "$(CLASSPATH):/home/user/Working-Dir/JC_All/Applets/Server/build/classes/"
JAVA                ?= java
JC                  ?= javac

# Compute .class files for build/classes dir
CLASSES = $(SOURCES:src/%.java=$(BUILD_CLASSES_DIR)/%.class)

# Default target
default: cap

# Compile .java -> .class
$(CLASSES): $(SOURCES)
	mkdir -p $(BUILD_CLASSES_DIR)
	$(JC) $(JFLAGS) $(SOURCES)

# Convert .class files -> CAP
cap: $(CLASSES)
	mkdir -p $(BUILD_JAVACARD_DIR)
	$(JAVACARD_SDK_DIR)/bin/converter.sh \
		-target 3.0.4 \
		-out JCA -out CAP -out EXP \
		-d $(BUILD_JAVACARD_DIR) \
		-classdir $(BUILD_CLASSES_DIR) \
		-exportpath $(JAVACARD_EXPORT_DIR) \
		-applet $(APPLET_AID) $(APPLET_NAME) \
		$(PACKAGE_NAME) $(PACKAGE_AID) $(PACKAGE_VERSION)

# Clean target
.PHONY: clean
clean:
	$(RM) -rf $(BUILD_DIR)
