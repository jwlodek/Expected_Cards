# Card Color Gambling Game README
### Author: Jakub Wlodek
### April 2018

## Running ExpectedCards.java
--------------------------------
To run the program, simply compile with:

```
> javac ExpectedCards.java
```

and run with:

```
> java ExpectedCards
```

There are three possible run configurations:

No additional arguments: finds expected value at the start of the game.
-h or --help flag: prints help message
Two integer arguments: passes a starting configuration of the players hand,
and finds expected value for said starting configuration.
---------------------------------

## Method:

My initial method I decided to use was a recursive method, that calculates
each previous expected value working back from the final position, with the base 
cases where if there are 0 red cards remaining in the deck the expected value must be 0,
and the case where there are 0 black cards in the deck, making the expected value the
current player score. From there, I realized that this problem could be better solved
using a dynamic programming table. I started at the base case and filled in the 26 by 26 
table, where the axes of the table represent the number of each of the type of card remaining
in the deck. The formula in use is: Expected(R,B) = P(Black)*Expected(R,B-1)+P(Red)*Expected(R-1,B),
where R is the number of remaining red cards, B is the number of remaining black cards, and
P(X) is the probability of pulling a card of X color at the current position. Working back
from my base cases and filling out the table, to find the desired result I simply 
returned the correct table position. The starting position is the position with 26 of each color
remaining. The next addition I made to my program, was to check each result against the previous
expected values in order for the player to "quit while they're ahead." This is because
if a player has good luck and pulls many black cards at the start of the game, their
chance of pulling another black card gets smaller and smaller, and it is beneficial
to stop playing at a certain point. Once I finished, I worked out the expected value at the start of
the game to be $2.62.