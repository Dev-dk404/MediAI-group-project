var MyToken = artifacts.require("./MyToken.sol");

var MyTokenSalee = artifacts.require("./MyTokenSalee.sol");
var Storage = artifacts.require("./Storage.sol");

module.exports = async function(deployer) {
    let addr = await web3.eth.getAccounts();
    //deploy token second variable is number of tokens
    await deployer.deploy(MyToken, 1000000000);
    //deploy token using rate(wei) and address and token
    await deployer.deploy(MyTokenSalee, 1, addr[0], MyToken.address);
    //get token instance
    let tokenInstance = await MyToken.deployed();
    //transfer all tokens to tokensale
    await tokenInstance.transfer(MyTokenSalee.address, 1000000000);

    //deploy storage contract
    await deployer.deploy(Storage)

    //print storage contract address when successfully deployed
    await Storage.deployed()
        .then(console.log(Storage.address))
};
