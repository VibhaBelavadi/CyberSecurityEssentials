# Differential Privacy based Access control

CS 6301.0U1 Project		
Name: Vibha Belavadi	
Netid: vxb141530

Project on: Differential Privacy based Access control

This project is an implementation of Differential Privacy based access control taken from the book: On the Move to Meaningful Internet Systems: OTM 2016 Conferences.

In this project based on the privacy clearance of the user (low risk, medium risk, medium-high risk and high risk), the data access is provided with or without noise addition.

A low risk user, typically the admin/owner of the database can view either the complete table or the aggregated values, aggregation being based on education and income levels and values being the number of records for the given education income value.

As the user privacy clearance decreases (goes from medium risk to high risk), the randomness added to the data increases and error from the actual value of records also increases. This deviation is measured by the normalized error rate.

The following use cases were implemented along, as adapted from the paper. The final column is the parameter used to attain that level of differential privacy access:

Role            | Operation          | Risk        | Utility     | Privacy Clearance parameter
--------------- | ------------------ | ----------- | ----------- | ----------------------------
HR Manager      | HR view (internal) | Low         | Full access | Tϵ > 1
HR Manager      | HR view (external) | Medium      | Aggregated  | Tϵ ∈ ]0.1, 1]
HR Developer    | Testing data       | Medium-high | Anonymized  | Tϵ ∈ ]0.05, 0.1]
HR Benchmarking | Benchmarking       | High        | Anonymized  | Tϵ ≤ 0.05

Here Tϵ= ϵ if data sanitization is required. The dataset used is adult.csv file
The user is required to log into his role and operation. Upon doing so, the program automatically picks up risk level of the user and grants him access accordingly.


