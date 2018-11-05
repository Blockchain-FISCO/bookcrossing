package com.hust.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class Book extends Contract {
    private static String BINARY = "6060604052341561000c57fe5b5b33600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b6112e88061005f6000396000f30060606040523615610097576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063399b576d146100995780635301d33b146100b957806371a305bf1461011c57806384fbf248146101b65780638917d33c1461029b578063bdfde43c14610384578063c7eb747e14610441578063ca5140c91461049b578063d0296612146104bb575bfe5b34156100a157fe5b6100b760048080359060200190919050506105d6565b005b34156100c157fe5b61011a600480803590602001909190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506106d9565b005b341561012457fe5b610174600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506107ba565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156101be57fe5b6101d4600480803590602001909190505061084e565b6040518080602001841515151581526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182810382528581815181526020019150805190602001908083836000831461025f575b80518252602083111561025f5760208201915060208101905060208303925061023b565b505050905090810190601f16801561028b5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34156102a357fe5b610382600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506109b7565b005b341561038c57fe5b6103b8600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610c33565b6040518080602001828103825283818151815260200191508051906020019080838360008314610407575b805182526020831115610407576020820191506020810190506020830392506103e3565b505050905090810190601f1680156104335780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561044957fe5b610499600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610d1b565b005b34156104a357fe5b6104b96004808035906020019091905050610e2d565b005b34156104c357fe5b610513600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610f1c565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018381526020018060200182810382528381815181526020019150805190602001908083836000831461059a575b80518252602083111561059a57602082019150602081019050602083039250610576565b505050905090810190601f1680156105c65780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b6105de6110ca565b600060006105ea6110de565b6105f38561084e565b8094508195508296505050506060604051908101604052808581526020016001151581526020018373ffffffffffffffffffffffffffffffffffffffff1681525090508060086000878152602001908152602001600020600082015181600001908051906020019061066692919061111e565b5060208201518160010160006101000a81548160ff02191690831515021790555060408201518160010160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509050505b5050505050565b6106e16110de565b606060405190810160405280838152602001600015158152602001600073ffffffffffffffffffffffffffffffffffffffff1681525090508060086000858152602001908152602001600020600082015181600001908051906020019061074992919061111e565b5060208201518160010160006101000a81548160ff02191690831515021790555060408201518160010160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509050505b505050565b60006003826040518082805190602001908083835b602083106107f257805182526020820191506020810190506020830392506107cf565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505b919050565b6108566110ca565b600060006108626110de565b6008600086815260200190815260200160002060606040519081016040529081600082018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561091a5780601f106108ef5761010080835404028352916020019161091a565b820191906000526020600020905b8154815290600101906020018083116108fd57829003601f168201915b505050505081526020016001820160009054906101000a900460ff161515151581526020016001820160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152505090508060000151816020015182604001518292509350935093505b509193909250565b6109bf61119e565b6109c76111dc565b6109cf6110de565b6060604051908101604052803373ffffffffffffffffffffffffffffffffffffffff168152602001878152602001868152509250826004886040518082805190602001908083835b60208310610a3a5780518252602082019150602081019050602083039250610a17565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002019080519060200190610ada92919061111e565b5090505060406040519081016040528088815260200185815250915081600660008881526020019081526020016000206000820151816000019080519060200190610b2692919061111e565b506020820151816001019080519060200190610b4392919061111e565b50905050606060405190810160405280602060405190810160405280600081525081526020016001151581526020013373ffffffffffffffffffffffffffffffffffffffff16815250905080600860008881526020019081526020016000206000820151816000019080519060200190610bbe92919061111e565b5060208201518160010160006101000a81548160ff02191690831515021790555060408201518160010160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509050505b50505050505050565b610c3b6110ca565b600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d0e5780601f10610ce357610100808354040283529160200191610d0e565b820191906000526020600020905b815481529060010190602001808311610cf157829003601f168201915b505050505090505b919050565b80600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209080519060200190610d6e929190611203565b50336003826040518082805190602001908083835b60208310610da65780518252602082019150602081019050602083039250610d83565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506002600081548092919060010191905055505b50565b610e356110de565b606060405190810160405280602060405190810160405280600081525081526020016001151581526020013373ffffffffffffffffffffffffffffffffffffffff16815250905080600860008481526020019081526020016000206000820151816000019080519060200190610eac92919061111e565b5060208201518160010160006101000a81548160ff02191690831515021790555060408201518160010160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055509050505b5050565b60006000610f286110ca565b610f3061119e565b6004856040518082805190602001908083835b60208310610f665780518252602082019150602081019050602083039250610f43565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020606060405190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152602001600282018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561109e5780601f106110735761010080835404028352916020019161109e565b820191906000526020600020905b81548152906001019060200180831161108157829003601f168201915b50505050508152505090508060000151816020015182604001518090509350935093505b509193909250565b602060405190810160405280600081525090565b6060604051908101604052806110f2611283565b8152602001600015158152602001600073ffffffffffffffffffffffffffffffffffffffff1681525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061115f57805160ff191683800117855561118d565b8280016001018555821561118d579182015b8281111561118c578251825591602001919060010190611171565b5b50905061119a9190611297565b5090565b606060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016111d6611283565b81525090565b6040604051908101604052806111f0611283565b81526020016111fd611283565b81525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061124457805160ff1916838001178555611272565b82800160010185558215611272579182015b82811115611271578251825591602001919060010190611256565b5b50905061127f9190611297565b5090565b602060405190810160405280600081525090565b6112b991905b808211156112b557600081600090555060010161129d565b5090565b905600a165627a7a7230582055252ba718f10f44d22f41d817bf3ba80fbabdc7fa40f1d9ca8146e0b2cd5bc90029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"_bookId\",\"type\":\"uint256\"}],\"name\":\"resetBookStatus\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_bookId\",\"type\":\"uint256\"},{\"name\":\"_studId\",\"type\":\"string\"}],\"name\":\"borrowBook\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_schoolName\",\"type\":\"string\"}],\"name\":\"getAddressOfSchool\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_bookId\",\"type\":\"uint256\"}],\"name\":\"checkBookStatus\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_studId\",\"type\":\"string\"},{\"name\":\"_bookId\",\"type\":\"uint256\"},{\"name\":\"_emailAddr\",\"type\":\"string\"},{\"name\":\"_bookName\",\"type\":\"string\"}],\"name\":\"registerStudent\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_schoolAddress\",\"type\":\"address\"}],\"name\":\"getSchoolOfAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_schoolName\",\"type\":\"string\"}],\"name\":\"registerSchool\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_bookId\",\"type\":\"uint256\"}],\"name\":\"returnBook\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_studId\",\"type\":\"string\"}],\"name\":\"getStudent\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"schoolAddr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"actId\",\"type\":\"uint256\"}],\"name\":\"returnRegisterStudent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"departName\",\"type\":\"string\"}],\"name\":\"printData\",\"type\":\"event\"}]";

    private Book(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private Book(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private Book(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private Book(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static List<ReturnRegisterStudentEventResponse> getReturnRegisterStudentEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("returnRegisterStudent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ReturnRegisterStudentEventResponse> responses = new ArrayList<ReturnRegisterStudentEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ReturnRegisterStudentEventResponse typedResponse = new ReturnRegisterStudentEventResponse();
            typedResponse.schoolAddr = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.actId = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ReturnRegisterStudentEventResponse> returnRegisterStudentEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("returnRegisterStudent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ReturnRegisterStudentEventResponse>() {
            @Override
            public ReturnRegisterStudentEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ReturnRegisterStudentEventResponse typedResponse = new ReturnRegisterStudentEventResponse();
                typedResponse.schoolAddr = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.actId = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public static List<PrintDataEventResponse> getPrintDataEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("printData", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<PrintDataEventResponse> responses = new ArrayList<PrintDataEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            PrintDataEventResponse typedResponse = new PrintDataEventResponse();
            typedResponse.departName = (Utf8String) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<PrintDataEventResponse> printDataEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("printData", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, PrintDataEventResponse>() {
            @Override
            public PrintDataEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                PrintDataEventResponse typedResponse = new PrintDataEventResponse();
                typedResponse.departName = (Utf8String) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Future<TransactionReceipt> resetBookStatus(Uint256 _bookId) {
        Function function = new Function("resetBookStatus", Arrays.<Type>asList(_bookId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void resetBookStatus(Uint256 _bookId, TransactionSucCallback callback) {
        Function function = new Function("resetBookStatus", Arrays.<Type>asList(_bookId), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> borrowBook(Uint256 _bookId, Utf8String _studId) {
        Function function = new Function("borrowBook", Arrays.<Type>asList(_bookId, _studId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void borrowBook(Uint256 _bookId, Utf8String _studId, TransactionSucCallback callback) {
        Function function = new Function("borrowBook", Arrays.<Type>asList(_bookId, _studId), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Address> getAddressOfSchool(Utf8String _schoolName) {
        Function function = new Function("getAddressOfSchool", 
                Arrays.<Type>asList(_schoolName), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<List<Type>> checkBookStatus(Uint256 _bookId) {
        Function function = new Function("checkBookStatus", 
                Arrays.<Type>asList(_bookId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> registerStudent(Utf8String _studId, Uint256 _bookId, Utf8String _emailAddr, Utf8String _bookName) {
        Function function = new Function("registerStudent", Arrays.<Type>asList(_studId, _bookId, _emailAddr, _bookName), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void registerStudent(Utf8String _studId, Uint256 _bookId, Utf8String _emailAddr, Utf8String _bookName, TransactionSucCallback callback) {
        Function function = new Function("registerStudent", Arrays.<Type>asList(_studId, _bookId, _emailAddr, _bookName), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Utf8String> getSchoolOfAddress(Address _schoolAddress) {
        Function function = new Function("getSchoolOfAddress", 
                Arrays.<Type>asList(_schoolAddress), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> registerSchool(Utf8String _schoolName) {
        Function function = new Function("registerSchool", Arrays.<Type>asList(_schoolName), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void registerSchool(Utf8String _schoolName, TransactionSucCallback callback) {
        Function function = new Function("registerSchool", Arrays.<Type>asList(_schoolName), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> returnBook(Uint256 _bookId) {
        Function function = new Function("returnBook", Arrays.<Type>asList(_bookId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void returnBook(Uint256 _bookId, TransactionSucCallback callback) {
        Function function = new Function("returnBook", Arrays.<Type>asList(_bookId), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<List<Type>> getStudent(Utf8String _studId) {
        Function function = new Function("getStudent", 
                Arrays.<Type>asList(_studId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public static Future<Book> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Book.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Book> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Book.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Book load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Book(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static Book load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Book(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static Book loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Book(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static Book loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Book(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class ReturnRegisterStudentEventResponse {
        public Address schoolAddr;

        public Uint256 actId;
    }

    public static class PrintDataEventResponse {
        public Utf8String departName;
    }
}
