package Bagua.Base;

public enum EnumCoin
{
    _1ST, _2GIVE, ABY, ADT, AEON, AGRS, AMP, ANS, ANT, APX, ARDR, ARK, AUR, BAT,BAY,
    BCY, BCN, BELA, BNT, BRK, BRX, BSD, BLK, BTA, BTCD, BTS, BTC, BTM, BURST, BYC,
    CANN, CFI, CLAM, CLOAK, CLUB, COVAL, CPC, CRB, CRW, CURE, DAR, DASH, DCR, DCT, DGB,
    DGD, DMD, DOGE, DOPE, DRACO, DTB, DYN, EBST, EDG, EFL, EGC, EMC, EMC2, ERC, ETC,
    ETH, EXCL, EXP, FAIR, FCT, FLDC, FLO, FTC, FUN, GAM, GAME, GBG, GBYTE, GCR, GEO,
    GLD, GNO, GNT, GOLOS, GRC, GRS, GUP, HKG, HMQ, HUC, INCNT, INFX, IOC, ION, IOP,
    KMD, KORE, LBC, LGD, LMC, LSK, LTC, LUN, MAID, MCO, MEME, MLN, MONA, MUE, MUSIC, 
    MYR, MYST, NAUT, NAV, NBT, NEOS, NLG, NMR, NMC, NXC, NXT, NOTE, OK, OMNI, PAY,
    PASC, PDC, PINK, PIVX, PKB, POT, PPC, PTC, PTOY, QRL, QWARK, RADS, RBY, RDD, REP,
    RISE, RLC, RIC, SBD, SC, SEC, SEQ, SHIFT, SIB, SJCX, SLR, SLS, SNGLS, SNRG, SNT, 
    SPHR, SPR, START, STEEM, STR, STRAT, SWIFT, SWT, SYNX, SYS, THC, TIME, TKN, TRIG, 
    TRST, TRUST, TX, UBQ, UNB, UNO, VIA, VOX, VRC, VRM, VTC, VTR, WAVES, WINGS, XAUR, 
    XBB, XBC, XCP, XDN, XEL, XEM, XLM, XMG, XMR, XPM, XRP, XST, XVC, XVG, XWC, XZC, ZCL,
    ZEC, ZEN,
    // need to sort 
    RRT, BCC, BCU, IOT, EOS, SAN, // <- bitfinex 
    ZMC, QTUM, GXS // <- Yunbi
    ;

    public String toString()
    {
        if(super.toString().startsWith("_"))
        {
            return super.toString().substring(1);
        }
        return super.toString();
    }
};