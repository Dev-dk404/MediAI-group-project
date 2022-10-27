pragma solidity >=0.6.0;
import "./Crowdsale.sol";

//used Crowdsale token from openzepplin as template for Token Sale
contract MyTokenSalee is Crowdsale {

    constructor(
        uint256 rate, // rate in TKNbits
        address payable wallet,
        IERC20 token
    )
        Crowdsale(rate, wallet, token)
        public
    {
    }
}
