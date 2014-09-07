# NFC Bi-Directional data over HCE

There are following three modules in this project

1. **[libHCE](#libHCE)** - The library that offers both reader and emulator APIs   
2. **[CardEmulatorApp](#CardEmulatorApp)** - App with example implementation of emulator    
3. **[CardReaderApp](#CardReaderApp)** - App with example implementation of the reader    

A completely bi-directional data channel is possible to establish, 
and packets of 255 bytes (64kbytes, if both devices so support) 
can be interchanged between the devices. But due to the nature of 
implementation of the NFC stack, only the device that is behaving 
as the reader can intiate the connection.



###libHCE    

###CardEmulatorApp    

###CardReaderApp    

