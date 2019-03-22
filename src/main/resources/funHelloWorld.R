printHelloWorld <- function() {
    # assigning a string to the variable
    helloWorld <- "Hello, World!"

    #printing the variable
    print(helloWorld)

    # here we concatenate the created variable with some extra information
    # R automatically returns the last statement, so we're done here!
    paste(helloWorld, "executed in R")
}
