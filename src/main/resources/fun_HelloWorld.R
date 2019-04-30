printHelloWorld <- function() {
    # Assigning a string to the variable
    helloWorld <- "Hello, World!"

    # Printing the variable, just because
    print(helloWorld)

    # Here we concatenate the created variable with some extra information, fancy stuff!
    # R automatically returns the last statement, so we're done here!
    paste(helloWorld, "executed in R")
}
