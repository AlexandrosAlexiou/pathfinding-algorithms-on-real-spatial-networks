JAVAC=javac -d out/
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

default: $(classes)

clean:
	rm -f out/*.class

%.class: %.java
	$(JAVAC) $<

.PHONY: all program clean