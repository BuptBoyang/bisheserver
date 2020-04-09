package indi.boyang.bishe.util;

import indi.boyang.bishe.controller.UserController;
import indi.boyang.bishe.model.User;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class MyWeb3j {
    static Admin admin = Admin.build(new HttpService("http://192.168.169.128:8545/"));
    static String adminAddress = "0x91aB9203968290402EdE6484dA278F27139cB48b";
    private static Credentials credentials = Credentials.create("013F4C257FE839BC5F3567F7846797646057FEEEF9BF54F5D7208CFE5C850B81");
    static String contractAddress = "0x8858b384ad8D44d2c4C6ca03F8E22738142758c6";
    static IPVC contract = IPVC.load(contractAddress,admin,credentials,new BigInteger("0"),Contract.GAS_LIMIT);

    public static String[] newAddress(String password){
        String[] result = {"",""};
        String address = "";

        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

            String sPrivateKeyInHex = privateKeyInDec.toString(16);

            WalletFile aWallet = Wallet.createLight(password, ecKeyPair);
            address = aWallet.getAddress();
            contract.transfer(address, Convert.toWei("100", Convert.Unit.ETHER).toBigInteger()).sendAsync();
            result[0] = address;
            result[1] = sPrivateKeyInHex;
            System.out.println("new account address " + result[0]);
            System.out.println("pk " + result[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

/*
        try {
            NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
            address = newAccountIdentifier.getAccountId();
            System.out.println("new account address " + address);
            contract.transfer(address, Convert.toWei("100", Convert.Unit.ETHER).toBigInteger()).sendAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
 */
        return result;
    }

    public static void transferERC20Token(String senderID, String receiverID, String value) throws Exception {
        User sender = UserController.userRepository.findById(senderID).get();
        User receiver = UserController.userRepository.findById(receiverID).get();
        Credentials userCredential = Credentials.create(sender.getPrivateKey());
        System.out.println(senderID);
        System.out.println(sender.toString());
        System.out.println(userCredential);
        IPVC ipvc = IPVC.load(contractAddress,admin,userCredential,new BigInteger("0"),Contract.GAS_LIMIT);
        ipvc.transfer(receiver.getAddress(),Convert.toWei(value, Convert.Unit.ETHER).toBigInteger()).sendAsync();
    }


    public static int getERC20Balance(String address) throws Exception {
        BigInteger balance = contract.balanceOf(address).send();
        return balance.divide(Convert.toWei("1", Convert.Unit.ETHER).toBigInteger()).intValue();
    }

}
