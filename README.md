[Looking for modern Forge?](https://github.com/snackbag/TT20-Forged)\
[Looking for Fabric?](https://github.com/snackbag/TT20)

# TT20 Gregged

This is a port of the TT20 Forge version for 1.12.2, and in the future 1.7.10 + 1.8.9.\
TT20 helps reduce lag by optimizing how ticks work when the server's TPS is low.

**THIS VERSION MAY INHABIT UNEXPECTED BEHAVIOUR!** I have never developed for 1.12.2, so be very critical of my code.

## What does TT20 stand for?

TT20 is an abbreviation of the `ticks*tps/20` formula. It's used to calculate the amount of ticks something takes, while
taking the TPS into account.

## Caveats

TT20 only fixes the symptoms of TPS lag, not the actual lag. It re-calculates the amount of ticks things take. For
example, when you're breaking a block it takes the original break time, multiplies it by ticks and divides it by 20 (the
maximum TPS). This ensures that the end user feels almost no lag, even if the TPS is very low.

## Roadmap

- [X] Block break delay
- [X] Eating delay
- [X] Item pickup delay
- [X] Block entity tick acceleration (experimental)
- [ ] Entity death animation(?)
- [ ] Block state delay
- [X] Portal delay
- [X] Sleeping delay
- [X] Potion delay
- [X] Fluid spread speed
- [X] Random tickspeed acceleration
- [X] Time acceleration

If you believe there is features missing, please tell us by creating a new issue (yes, also if you want compatibility
with other mods!)
