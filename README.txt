Simple Credit Card Process

WHAT IS IT:

This is a simple credit card process written in Java. It supports two process modes:
1. Run with file process
2. Run with command line process


It does following transactions:
- Add credit card with name, card number and credit limit, start with zero balance and ACTIVE status initially.
- Charge credit card with certain amount of money and increase balance, if exceeds credit limit, transaction will be discarded.
- Pay credit with certain amount of money and decrease balance.
- Verify whether credit card number is valid using Luhn algorithm. Fail credit card number validation will result in an ERROR status
- Generate summary report of all credit cards.
- Read transaction data from file or stdin.
- Perform serveral JUnit test case.
- Log every transactions with Braintree processor authorization repsonse code and message 
https://developers.braintreepayments.com/ios+ruby/reference/general/processor-responses/authorization-responses


HOW TO USE:
Prerequisite: JDK 1.6 and JUnit 4 library

Run with file process:
1. In terminal, run command "java -jar braintree.jar /path/to/transaction/file"
2. It reads transaction file line by line, process transaction and print out credit card report and log message.
3. After all, program exits.

Run with command line process:
1. In terminal, run command "java -jar braintree.jar"
2. Enter each transaction line by line(hit "ENTER" to next line)
3. When it encount "process" command, it stops enterring transaction to perform transactions, print out credit card report and log message. Then, program exits.


ASSUMPTION:
1. Assume one person hold only one credit card so that credit card and person are one-to-one relationship in this program.
2. Duplicate 'Add' transaction for same card is considered as update limit.
3. All money number are whole number.
4. Credit card has only one currency.
5. Command line mode process will stop accept incoming data once it encounters 'process' command.
6. Only has 3 types of transactions, 'Add', 'Charge' and 'Credit'.
7. Each line has 3 or 4 words such as 'Add Tom 4111111111111111 $1000' or 'Charge Tom $500'


DESIGN:
1. Use abstract class AbstractTransaction to be inherited, intend to be extended to different transactions. Currently, it supports add account transaction, charge credit card transaction, pay credit card transaction. In future, it may include Charge/Credit rollback transaction, credit card update transaction.
2. Design two credit card process modes FileTransactionReader and CommandLineTransactionReader with implementing TransactionReader interface.
3. Log every transaction with meaningful message and error code.
4. Use static method in LuhnValidator to validate credit card number return true/false.
5. Each transaction has ID can be uniquely identified, it could be generated upfront then persist to database or a identifier returned from database. 
6. Because of FileTransactionReader and CommandLineTransactionReader share core logics, only difference is handling the data input, I just write test case for FileTransactionReader.
7. Run test case against some resources file in resource folder.


REASON TO PICK JAVA:
1. Strong type language and my most favorite programming language and development environment
2. Enterprise application supprot.
3. Mutiple frameworks regarding distributed system support Java and are developed in Java, quick and easy to integrate with other platforms.


TEST CASE:
1. Import project braintree to Eclipse/InteliJIDEA run with JUnit library
2. Run individual test case. AppTest.java, FileTransactionReaderTest.java and LuhnValidatorTest.java


LIMITATION:
1. No currency conversion.
2. One person can only obtain one credit card.
3. Limited transaction type.
4. No multi-thread process support.
5. No configurable test plan.





