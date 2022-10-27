pragma solidity >=0.6.0;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

//used openzeppelin ERC20 token standard as template for our Medi-coin token
contract MyToken is ERC20 {
    constructor(uint256 initialSupply) ERC20("Medi-Coin Token", "MEDI") public {
        _mint(msg.sender, initialSupply);
        _setupDecimals(0);
    }
}