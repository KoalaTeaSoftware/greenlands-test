# Grouping Strategy
The feature files are actually all independent, and could be run in any order, and any sub-selection.
 
The ordering of the folders (starting their names with a number) is only used to help with fault triage. If the tests for the infrastructure are run first, and show any faulty behaviour, then that may help decide if a fault detected in, say the contact form, is a real fault. 

Similarly, it could be that a test runner that was only interested in the infrastructure tests could be used as a smoke tester (although this is not advocated).