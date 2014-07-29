by Henry Chan
hchan@apache.org
July 27/2014
--------------------------------------------------------------
Each testcase takes about 5 - 25s to run
To build this program from Windows:
build.bat

to run it:
run.bat

When running, run.bat will output the soln to output.dat (via redirection STDOUT - I like my println's ;))
This will also generate a grid_x.html file where x is the number of the testcase.  I use this
as a debugging tool

// @TODO
// in theory, we should actually also do a search in REVERSE
// from ex to en, ... in fact I tried it and got slightly different results =(
// should create merge algorithm that goes both ways and finds the intersection

C:\workspace\eaOriginInterview>build

[just output from the batch]C:\workspace\eaOriginInterview>javac src\ca\henrychan\gascalculator\*.java

C:\workspace\eaOriginInterview>time
The current time is: 15:56:14.26
Enter the new time:
C:\workspace\eaOriginInterview>run && time

[just output from the batch]C:\workspace\eaOriginInterview>java -cp "src" ca.henrychan.gascalculator.GasCalulator  1>output.dat
The current time is: 15:58:41.08
Enter the new time:

C:\workspace\eaOriginInterview>



And yes, this assignment was kinda fun ;)

