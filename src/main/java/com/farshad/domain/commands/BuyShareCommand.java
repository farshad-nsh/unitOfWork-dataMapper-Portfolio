package com.farshad.domain.commands;


import com.farshad.domain.Share;
import com.farshad.infrastructure.annotations.CommandHandler;
import com.farshad.infrastructure.commanddispatcher.AbstractCommandListener;
import com.farshad.infrastructure.protos.generated.domain.BondProtos;

@CommandHandler(ExtMessageClasses = BondProtos.Bond.class)
public class BuyShareCommand extends AbstractCommandListener<BondProtos.Bond> {

    Share share;

    public BuyShareCommand(Share share) {
        this.share = share;
    }

    @Override
    public void execute(BondProtos.Bond bond) {
        share.buy(bond.getValue());
    }
}